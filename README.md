dependencies:
org.yaml:snakeyaml:1.29
# Overrides examples
more examples in https://github.com/pixelfederation/pf-podtemplate-library/blob/master/Jenkinsfile-example
```groovy
// HOW TO OVERRIDE POD TEMPLATE WITH DOTT NOTATION
podTemplateYaml = getTemplate.Util([:], ['spec.nodeSelector.foo': 'bar' ])

//HOW TO OVERRIDE POD TEMPLATE WITH DOTT NOTATION AND DELETE EXISTING WITH NULL
getTemplate.UtilBuildxPhp([:], ['spec.nodeSelector.dedicated' : null , 'spec.nodeSelector.foo': 'bar' ])

//HOW TO OVERRIDE POD TEMPLATE WITH MULTIPLE KEYS
getTemplate.UtilBuildxPhp([:], ['spec.nodeSelector.foo' : 'bar' , 'spec.serviceAccountName': 'test' ])

//HOW TO OVERRIDE POD TEMPLATE WITH MAP
getTemplate.UtilBuildxPhp([:], ['spec': ['nodeSelector': ['foo' : 'bar'] , 'serviceAccountName': 'test' ]])

//HOW TO OVERRIDE POD TEMPLATE WITH MAP AND DELETE KEY
getTemplate.UtilBuildxPhp([:], ['spec': ['nodeSelector': ['foo' : 'bar', 'dedicated': null] , 'serviceAccountName': 'test' ]])

//ADD UTIL AND MULTIPLE SAME KANIKO CONTAINERS WITH DIFERENT NAMES, REMOVE EXISTING SELECTOR, ADD NEW SELECTOR, CHANGE SERVICE ACCOUNT
getTemplate.Jnlp('util': [:], 'kaniko': [names: ["k1", "k2"]], ['spec.nodeSelector.dedicated' : null , 'spec.nodeSelector.foo': 'bar' , 'spec.serviceAccountName' : 'foobar' ])

// NOW THERE IS _default.yaml template used if container name don't match any template. `nonexistentTemplate` will become container with _default template and can be fully overrided
getTemplate.Jnlp('nonexistentTemplate': [:])
```

# Shorhands explaind
```groovy
getTemplate('jnlp': [:], 'buildx':[:], 'php': [:]) // same as below
getTemplate.UtilBuildxPhp()
// it just define which containers without any overrides


getTemplate.UtilBuildxPhp('php': ['name': 'my-build-php']) //same as below
getTemplate.UtilBuildx('php': ['name': 'my-build-php'])
//becuse in both cases we are asking to include php template into containers and overriding container name for php container
```


# Multiple same containers with `names`
names must be list
```groovy
getTemplate.UtilBuildx('php':['names': ['my-first-build-php', 'my-another-php']])
// any override will override both of them
```

# Ad-hoc container
If you choose container name which don't match any template eg `my-non-existent`,  _default.yaml template ( debian-slim image ) will be used and can be overrided in place
```groovy
getTemplate.Util('my-non-existent':[:])
```