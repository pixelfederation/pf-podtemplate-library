package tech.pxfd

@Grab("org.yaml:snakeyaml:1.29")
import org.yaml.snakeyaml.Yaml
import groovy.json.JsonSlurper
import groovy.json.JsonOutput

//check this if want to loop inside resources without knowing file names
// https://stackoverflow.com/questions/61088491/how-to-access-to-a-static-file-in-resources-folder-of-the-shared-library-from-wi

class Utils {
    def context

    Utils(pipelineContext) {
        this.context = pipelineContext
    }

    Map getResourcePod(String podName) {
        return new Yaml().load(this.context.libraryResource("pod/${podName}.yaml"))
    }

    Map getResourceContainer(String containerName, Map containerParams) {
        Map containerTemplate = [:]
        try {
            containerTemplate =  new Yaml().load(this.context.libraryResource("containers/${containerName}.yaml"))
        }
        catch(hudson.AbortException e) {
            this.context.println("INFO Unkown template `${containerName.toString()}`, using container _default.yaml template")
            containerTemplate =  new Yaml().load(this.context.libraryResource("containers/_default.yaml"))
        }
        containerTemplate['name'] = containerName
        return containerTemplate
    }

    public void checkRequiredAttributes(Map template) {
        List lst = []
        lst+= template.containsKey('image')
        return lst.every{ it == true }
    }

    public void error(String msg){
            this.context.error("ERROR " + msg)
    }

    @NonCPS
    public static final Map mapDeepCopy(Map originalMap) {
        return new JsonSlurper().parseText(JsonOutput.toJson(originalMap))
    }

    public static final Map removeUnmergable(Map m, List preserve = []) {
        List toRemove = ["names", "tag","_inherit", "env", "vol"]
        toRemove.each { it ->
            if (!preserve.contains(it)) {
                m.remove(it)
            }
        }
        return m
    }

    public static final Map mergeMap(Map left, Map right) {
        return right.inject(left.clone()) { map, entry ->
            if (map[entry.key] instanceof Map && entry.value instanceof Map) {
                map[entry.key] = this.mergeMap(map[entry.key], entry.value)
            } else if (map[entry.key] instanceof Collection && entry.value instanceof Collection) {
                map[entry.key] += entry.value
            } else {
                if (entry.value != null && entry.value != GlobalVars.deleteValueString) {
                    map[entry.key] = entry.value
                } else {
                    map.remove(entry.key)
                }
            }
            return map
        }
    }

    public static final String mapToYaml(Map m) {
        return new Yaml().dump(m)
    }

    public static final Map parseDots(String dotKey, def value) {
        Map tmpPropMap = [:]
        value = value ? value : GlobalVars.deleteValueString
        tmpPropMap[dotKey] = value
        def tmpProps = new Properties()
        tmpProps.putAll(tmpPropMap)
        return (new ConfigSlurper().parse(tmpProps))
    }
}

/*
TODO allow templates from other repository

import jenkins.plugins.git.GitSCMSource;
modernSCM(
    [
      $class: 'GitSCMSource',
      remote: 'https://github.com/your-company/demo-library.git',
      credentialsId: 'github'
    ]
*/