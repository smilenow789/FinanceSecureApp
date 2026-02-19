# Terraform-Definition für Google Cloud Run, damit Checkov diese scannen kann.

resource "google_cloud_run_service" "finance_app" {
  name     = "finance-secure-app"
  location = "europe-west1"

  template {
    spec {
      containers {
        image = "docker.io/smilenwo789/financesecureapp:latest"
      }
    }
  }
}