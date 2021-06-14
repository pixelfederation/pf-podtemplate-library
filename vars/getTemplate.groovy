import tech.pxfd.Utils

//TODO provide simpler env definition then {name: CONFD_EXECUTE, value: 'true'}
// implement fixEnv function, and accept en vars in simpler eg ["CONFD_EXECUTE":"true"] notation

//TODO add suport for dot separeted nested keys like spec.resouces.requests.memory

//TODO if provided key not match know template, will crash, allow definitions for unkown containers

String call(Map config = [:]) {
  def utils = new Utils(this)
  Map podTemplate = utils.getResourcePod('default')

  podTemplate['spec']['containers'] = []

  Map containersFinalDef = [:]

  clones = [:]

  config.each{String containerName, Map containerParams ->

    Map baseResourceFromLibTemplate = utils.getResourceContainer(containerName)
    
    containerParams.each{ String key, def value ->
      switch(key) {
        case "tag":
          baseResourceFromLibTemplate["image"] = baseResourceFromLibTemplate["image"].split(":")[0] + ":" + value
        break
        case "names":
          value.each{ cname -> 
            clones[cname] = [:]
            clones[cname]["_inherit"] = containerName
          }
        break
      }
    }

    Map paramsToMerge = Utils.removeUnmergable(containerParams)
    Map merge = Utils.mergeContainers( baseResourceFromLibTemplate , paramsToMerge)
    podTemplate['spec']['containers'] += merge
    containersFinalDef[containerName] = merge
  }

  clones.each{String containerName, Map containerParams ->
    Map params = Utils.mapDeepCopy(containersFinalDef[containerParams["_inherit"]])
    params["name"] = containerName
    podTemplate['spec']['containers'] += params
    containersFinalDef[containerName] = params
  }

  return Utils.mapToYaml(podTemplate)
}