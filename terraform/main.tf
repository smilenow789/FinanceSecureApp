# Terraform-Definition für Google Cloud Run, damit Checkov diese scannen kann.

resource "google_cloud_run_service" "finance_app" {
  name     = "finance-secure-app"
  location = "us-central1"

  template {
    spec {
      containers {
        image = "docker.io/smilenwo789/financesecureapp:latest"
      }
    }
  }
}

# Simulation für Checkov:
# Fiktives Netzwerk für die Blauwunder GmbH
resource "google_compute_network" "finance_vpc" {
  name                    = "finance-network"
  auto_create_subnetworks = false
}

# SCHWACHSTELLE: Firewall-Regel mit offenen Ports für die ganze Welt
resource "google_compute_firewall" "allow_ssh_rdp_public" {
  name    = "finance-allow-ssh-public"
  network = google_compute_network.finance_vpc.name

  allow {
    protocol = "tcp"
    ports    = ["22", "3389"] # Kritisch: SSH und RDP Ports
  }

  # Lücke: 0.0.0.0/0 "Jeder im Internet darf zugreifen"
  source_ranges = ["0.0.0.0/0"]
}