dependencies:
org.yaml:snakeyaml:1.29


println(getTemplate('jnlp': [:], 'util': 'resources.requests.memory': '665m', env: ["TOMBO=kombo", "mambo=jAmBo"], vol:[ ['type':'configmap','name': 'my-configmap-name','path' :'/var/log' ] ] ]))

```
