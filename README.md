dependencies:
org.yaml:snakeyaml:1.29

TODO:
we need something like this, but you cannot pass two named parameters `kaniko` with same name
getTemplate.Util('kaniko': [names: ["kaniko-php"], 'resources.requests.memory': '2000m'],'kaniko': [names: [kaniko-nginx], 'resources.requests.memory': '100m' ])
