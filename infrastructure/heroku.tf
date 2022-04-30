resource "heroku_app" "rent_your_stuff_kagzz" {
  name   = "rent-your-stuff-kagzz"
  region = "eu"

  buildpacks = [
    "heroku/gradle"
  ]
}

resource "heroku_addon" "rent_your_stuff_kagzz_db" {
  app  = heroku_app.rent_your_stuff_kagzz.id
  plan = "heroku-postgresql:hobby-dev"
}

resource "heroku_pipeline" "rent_your_stuff_kagzz_pipeline" {
  name = "rent-your-stuff-kagzz-pipeline"
}

# Couple app to pipeline.
resource "heroku_pipeline_coupling" "kagzz_pipeline_coupling" {
  app      = heroku_app.rent_your_stuff_kagzz.id
  pipeline = heroku_pipeline.rent_your_stuff_kagzz_pipeline.id
  stage    = "staging"
}


// Add the GitHub repository integration with the pipeline.
resource "herokux_pipeline_github_integration" "pipeline_integration" {
  pipeline_id = heroku_pipeline.rent_your_stuff_kagzz_pipeline.id
  org_repo = "alexkagzz/jmix-rys"
}

// Add Heroku app GitHub integration.
resource "herokux_app_github_integration" "rent_your_stuff_kagzz_gh_integration" {
  app_id = heroku_app.rent_your_stuff_kagzz.uuid
  branch = "develop"
  auto_deploy = true
  wait_for_ci = true

  # Tells Terraform that this resource must be created/updated
  # only after the `herokux_pipeline_github_integration` has been successfully applied.
  depends_on = [herokux_pipeline_github_integration.pipeline_integration]
}