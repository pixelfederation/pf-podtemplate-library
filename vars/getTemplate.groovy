
import tech.pxfd.GetTemplate
//TODO add suport for dot separeted nested keys like spec.resouces.requests.memory

//TODO if provided key not match know template, will crash, allow definitions for unkown containers

//TODO switch this.context.echo to Utils.echo

String call(Map config = [:]) {
  def template = new GetTemplate(this, config)
  return template.render()
}

String Jnlp(Map config = [:]) {
  // just define keys, props are from resources/container/{key} templates
  config['jnlp'] = [:]

  def template = new GetTemplate(this, config)
  return template.render()
}

String Util(Map config = [:]) {
  // just define keys, props are from resources/container/{key} templates
  config['jnlp'] = [:]
  config['util'] = [:]

  def template = new GetTemplate(this, config)
  return template.render()
}

String UtilKaniko(Map config = [:]) {
  // just define keys, props are from resources/container/{key} templates
  config['jnlp'] = [:]
  config['util'] = [:]
  // for ecr helper, we have optional configmap defined on pod template
  // if configmap not present, empty mount path will be created on kaniko container - /kaniko/.docker
  config['kaniko'] = [:]

  def template = new GetTemplate(this, config)
  return template.render()
}

String UtilKanikoPhp(Map config = [:]) {
  // just define keys, props are from resources/container/{key} templates
  config['jnlp'] = [:]
  config['util'] = [:]
  // for ecr helper, we have optional configmap defined on pod template
  // if configmap not present, empty mount path will be created on kaniko container - /kaniko/.docker
  config['kaniko'] = [:]
  config['php'] = [:]

  def template = new GetTemplate(this, config)
  return template.render()
}

String UtilBuildx(Map config = [:]) {
  // just define keys, props are from resources/container/{key} templates
  config['jnlp'] = [:]
  config['util'] = [:]
  config['buildx'] = [:]

  def template = new GetTemplate(this, config)
  return template.render()
}

String UtilBuildxPhp(Map config = [:]) {
  // just define keys, props are from resources/container/{key} templates
  config['jnlp'] = [:]
  config['util'] = [:]
  config['buildx'] = [:]
  config['php'] = [:]

  def template = new GetTemplate(this, config)
  return template.render()
}
