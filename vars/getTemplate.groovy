
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
  conatinersConf['jnlp'] = [:]

  def template = new GetTemplate(this, conatinersConf, podConf)
  return template.render()
}

String Util(Map conatinersConf = [:], Map podConf = [:]) {
  // just define keys, props are from resources/container/{key} templates
  conatinersConf['jnlp'] = [:]
  conatinersConf['util'] = [:]

  def template = new GetTemplate(this, conatinersConf, podConf)
  return template.render()
}

String UtilKaniko(Map conatinersConf = [:], Map podConf = [:]) {
  // just define keys, props are from resources/container/{key} templates
  conatinersConf['jnlp'] = [:]
  conatinersConf['util'] = [:]
  // for ecr helper, we have optional configmap defined on pod template
  // if configmap not present, empty mount path will be created on kaniko container - /kaniko/.docker
  conatinersConf['kaniko'] = [:]

  def template = new GetTemplate(this, conatinersConf, podConf)
  return template.render()
}

String UtilKanikoPhp(Map conatinersConf = [:], Map podConf = [:]) {
  // just define keys, props are from resources/container/{key} templates
  conatinersConf['jnlp'] = [:]
  conatinersConf['util'] = [:]
  // for ecr helper, we have optional configmap defined on pod template
  // if configmap not present, empty mount path will be created on kaniko container - /kaniko/.docker
  conatinersConf['kaniko'] = [:]
  conatinersConf['php'] = [:]

  def template = new GetTemplate(this, conatinersConf, podConf)
  return template.render()
}

String UtilBuildx(Map conatinersConf = [:], Map podConf = [:]) {
  // just define keys, props are from resources/container/{key} templates
  conatinersConf['jnlp'] = [:]
  conatinersConf['util'] = [:]
  conatinersConf['buildx'] = [:]

  def template = new GetTemplate(this, conatinersConf, podConf)
  return template.render()
}

String UtilPhp(Map conatinersConf = [:], Map podConf = [:]) {
  // just define keys, props are from resources/container/{key} templates
  conatinersConf['jnlp'] = [:]
  conatinersConf['util'] = [:]
  conatinersConf['php'] = [:]

  def template = new GetTemplate(this, conatinersConf, podConf)
  return template.render()
}

String UtilBuildxPhp(Map conatinersConf = [:], Map podConf = [:]) {
  // just define keys, props are from resources/container/{key} templates
  conatinersConf['jnlp'] = [:]
  conatinersConf['util'] = [:]
  conatinersConf['buildx'] = [:]
  conatinersConf['php'] = [:]

  def template = new GetTemplate(this, conatinersConf, podConf)
  return template.render()
}
