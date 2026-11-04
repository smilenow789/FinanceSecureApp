# Terraform-Definition für Google Cloud Run, damit Checkov diese scannen kann.
# Hinweis: Die Pipeline deployt das Image mit dem Commit-SHA als Tag
# (<DOCKER_USER>/financesecureapp:<sha>); ":latest" gilt hier nur als Platzhalter.

resource "google_cloud_run_service" "finance_app" {
  name     = "finance-secure-app"
  location = "europe-west1"

  template {
    spec {
      containers {
        image = "docker.io/smilenow789/financesecureapp:latest"
      }
    }
  }
}