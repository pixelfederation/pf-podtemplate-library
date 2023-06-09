package tech.pxfd
import tech.pxfd.Utils

// TODO volumes add check if path is present
// TODO simple shared volumes

class GetTemplate {
  
  private Map conatinersConf
  private Map podConf
  private context

// use def for both, check if conatinersConf just name or Map, same for podConf, allow multiple pod templates

  GetTemplate(def context, Map conatinersConf, Map podConf) {
    this.context = context

    this.conatinersConf = conatinersConf
    this.podConf = podConf
  }

  String render() {
    def utils = new Utils(this.context)

    Map podTemplate = utils.getResourcePod('default')

    podTemplate['spec']['containers'] = []

    Map containersFinalDef = [:]

    Map clones = [:]

    List mergeVolumes = []

    List uniqueNamesVolumes = [] //  per whole pod

    this.podConf.each { String podParamKey, def podParamValue ->
        if ( podParamKey.contains(".") ) {
            Map newPodParams = Utils.parseDots(podParamKey, podParamValue)
            this.podConf.remove(podParamKey)
            podTemplate = Utils.mergeMap(podTemplate, Utils.mapDeepCopy(newPodParams))
        }
    }

    podTemplate = Utils.mergeMap(podTemplate, this.podConf)

    this.conatinersConf.each{String containerName, Map containerParams ->
        List uniqueNamesMounts = [] // per container
        Map newParams = [:]
        containerParams.each{ String key, def value ->
            if ( key.contains(".") ) { //or check if its not Map or not a List
                newParams = Utils.parseDots(key, value)
                containerParams.remove(key)
                containerParams = Utils.mergeMap(containerParams, newParams)
            }
        }

        List mergeVolumesMounts = []
        List envs = []
        Map baseResourceFromLibTemplate = utils.getResourceContainer(containerName, containerParams)

        if ( ! utils.checkRequiredAttributes(baseResourceFromLibTemplate) ) {
            utils.error("Missing required arguments for template, eg. `image` , template: ${baseResourceFromLibTemplate.toString()}")
        }

        containerParams.each{ String key, def value ->
            switch(key) {
                case "tag":
                baseResourceFromLibTemplate["image"] = baseResourceFromLibTemplate["image"].split(":")[0] + ":" + value
                break
                case "names":
                if ( value instanceof List ) {
                    value.each{ String cname ->
                        clones[cname] = [:]
                        clones[cname]["_inherit"] = containerName
                    }
                } else {
                    utils.error("`names` must be a list")
                }
                break
                case "env":
                value.each { String envMixKeyVar ->
                    if ( envMixKeyVar.contains("=") ) {
                        List envMixKeyVarList = envMixKeyVar.split("=")
                        envs += ['name': envMixKeyVarList[0].toString().toUpperCase(), 'value': envMixKeyVarList[1].toString()]
                    } else {
                        this.context.echo("WARNING ignored env ${envMixKeyVar} missing `=` should be key=value")
                    }
                }
                containerParams.remove("env")
                break
                case "vol":
                value.each { Map volumeDefObj ->
                    Map volMount = [:]
                    Map volume = [:]
                    // ['name': 'log-pv', 'type': 'pvc', 'path' : '/var/log']
                    if (volumeDefObj.containsKey('configMapName')) {
                        this.context.echo("WARNING use `name` instead of configMapName")
                        volumeDefObj['name'] = volumeDefObj['configMapName']
                    }
                    if (volumeDefObj.containsKey('claimName')) {
                        this.context.echo("WARNING use `name` instead of claimName")
                        volumeDefObj['name'] = volumeDefObj['claimName']
                    }
                    if ( volumeDefObj['type'] == "host" && ! volumeDefObj.containsKey('hostPath') ) {
                        utils.error("volume type host - hostPath not provided")
                    }
                    if ( volumeDefObj['type'] == "nfs" && ( ! volumeDefObj.containsKey('server') || ! volumeDefObj.containsKey('name')) ) {
                        utils.error("volume type nfs - server not provided, eg 'efs.wtf.pxfd.tech:/' or volume name missing eg 'mynfs'")
                    }

                    switch(volumeDefObj['type']) {
                        case "configmap":
                        volume = ['configMap':['name': volumeDefObj['name'] ]]
                        //TODO options: item keys
                        break
                        case "pvc":
                        volume = ['persistentVolumeClaim': ['claimName': volumeDefObj['name'] ]]
                        break
                        case "nfs":
                        volume = ['nfs': ['server': volumeDefObj['server'].split(":")[0], 'path': volumeDefObj['server'].split(":")[1] ]]
                        break
                        case "host":
                        volume = ['hostPath':['path': volumeDefObj['hostPath'] ]]
                        break
                        case "empty":
                        volume = ['emptyDir': [:]]
                        break
                        case "secret":
                        //TODO
                        break
                    }

                    volume['name'] = volumeDefObj['name']
                    volMount['name'] = volumeDefObj['name']  //configMapName, claimName
                    volMount['mountPath'] = volumeDefObj['path']
                    // for pvc etc
                    if ( volumeDefObj.containsKey('readOnly') ) {
                        volMount['readOnly'] = volumeDefObj['readOnly']
                    }
                    // for configmap
                    if ( volumeDefObj.containsKey('optional') ) {
                        volMount['optional'] = volumeDefObj['optional']
                    }
                    if ( !uniqueNamesVolumes.contains(volume['name']) ) {
                        mergeVolumes += Utils.mapDeepCopy(volume)
                        uniqueNamesVolumes.add(volume['name'])
                    }
                    if ( !uniqueNamesMounts.contains(volMount['mountPath']) ) {
                        mergeVolumesMounts += Utils.mapDeepCopy(volMount)
                        uniqueNamesMounts.add(volMount['mountPath'])
                    }
                }
                containerParams.remove("vol")
                break
            }
        }

        Map paramsToMerge = Utils.removeUnmergable(containerParams)
        Map merge = Utils.mergeMap(baseResourceFromLibTemplate , paramsToMerge)
        if (!merge.containsKey('volumeMounts')) {
            merge['volumeMounts'] = []
        }
        if (!merge.containsKey('env')) {
            merge['env'] = []
        }
        merge['volumeMounts'].addAll(mergeVolumesMounts)
        merge['env'].addAll(envs)
        podTemplate['spec']['containers'] +=  Utils.mapDeepCopy(merge)
        containersFinalDef[containerName] =  Utils.mapDeepCopy(merge)
    }

    clones.each{String containerName, Map containerParams ->
        String name = containerParams["_inherit"]
        Map cloneDef = Utils.mapDeepCopy(containersFinalDef[name])
        cloneDef["name"] = containerName
        cloneDef = Utils.removeUnmergable(cloneDef,["env"])
        podTemplate['spec']['containers'] +=  Utils.mapDeepCopy(cloneDef)
        containersFinalDef[containerName] =  Utils.mapDeepCopy(cloneDef)
        podTemplate['spec']['containers'] = podTemplate['spec']['containers'].findAll { it.name != name }
    }

    podTemplate['spec']['volumes'].addAll(mergeVolumes)

    return Utils.mapToYaml(podTemplate).stripIndent()
  }
}
