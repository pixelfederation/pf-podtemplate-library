
import tech.pxfd.GetTemplate
//TODO add suport for dot separeted nested keys like spec.resouces.requests.memory

//TODO if provided key not match know template, will crash, allow definitions for unkown containers

//TODO switch this.context.echo to Utils.echo

String call(Map conatinersConf = [:], Map podConf = [:]) {
  def template = new GetTemplate(this, conatinersConf, podConf)
  return template.render()
}

String Jnlp(Map conatinersConf = [:], Map podConf = [:]) {
  // just define keys, props are from resources/container/{key} templates
  List containerList = ['jnlp']
  containerList.each { c ->
    if(!conatinersConf.containsKey(c)) {
      conatinersConf[c] = [:]
    }
  }

  def template = new GetTemplate(this, conatinersConf, podConf)
  return template.render()
}

String Util(Map conatinersConf = [:], Map podConf = [:]) {
  // just define keys, props are from resources/container/{key} templates
  List containerList = ['jnlp', 'util']
  containerList.each { c ->
    if(!conatinersConf.containsKey(c)) {
      conatinersConf[c] = [:]
    }
  }
  def template = new GetTemplate(this, conatinersConf, podConf)
  return template.render()
}

String UtilKaniko(Map conatinersConf = [:], Map podConf = [:]) {
  // just define keys, props are from resources/container/{key} templates
  List containerList = ['jnlp', 'util', 'kaniko']
  containerList.each { c ->
    if(!conatinersConf.containsKey(c)) {
      conatinersConf[c] = [:]
    }
  }
  def template = new GetTemplate(this, conatinersConf, podConf)
  return template.render()
}

String UtilKanikoPhp(Map conatinersConf = [:], Map podConf = [:]) {
  // just define keys, props are from resources/container/{key} templates
  List containerList = ['jnlp', 'util', 'kaniko', 'php']
  containerList.each { c ->
    if(!conatinersConf.containsKey(c)) {
      conatinersConf[c] = [:]
    }
  }
  def template = new GetTemplate(this, conatinersConf, podConf)
  return template.render()
}

String UtilBuildx(Map conatinersConf = [:], Map podConf = [:]) {
  // just define keys, props are from resources/container/{key} templates
  List containerList = ['jnlp', 'util', 'buildx']
  containerList.each { c ->
    if(!conatinersConf.containsKey(c)) {
      conatinersConf[c] = [:]
    }
  }
  def template = new GetTemplate(this, conatinersConf, podConf)
  return template.render()
}

String UtilPhp(Map conatinersConf = [:], Map podConf = [:]) {
  // just define keys, props are from resources/container/{key} templates
  List containerList = ['jnlp', 'util', 'php']
  containerList.each { c ->
    if(!conatinersConf.containsKey(c)) {
      conatinersConf[c] = [:]
    }
  }
  def template = new GetTemplate(this, conatinersConf, podConf)
  return template.render()
}

String UtilBuildxPhp(Map conatinersConf = [:], Map podConf = [:]) {
  // just define keys, props are from resources/container/{key} templates
  List containerList = ['jnlp', 'util', 'buildx', 'php']
  containerList.each { c ->
    if(!conatinersConf.containsKey(c)) {
      conatinersConf[c] = [:]
    }
  }
  def template = new GetTemplate(this, conatinersConf, podConf)
  return template.render()
}
