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

```
