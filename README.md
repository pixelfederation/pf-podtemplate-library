dependencies:
org.yaml:snakeyaml:1.29
```
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
