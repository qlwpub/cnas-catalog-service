# Build
custom_build (
  # name of the image
  ref = "catalog-service",
  # command to build the image
  command = "./gradlew bootBuildImage --imageName $EXPECTED_REF",
  # files to watch to trigger a new build
  deps = ["build.gradle.kts", "settings.gradle.kts", "src"]
)

# Deploy
# k8s_yaml ( ["k8s/deployment.yml", "k8s/service.yml"] )
k8s_yaml( kustomize("k8s") )

# Manage
k8s_resource ( "catalog-service", port_forwards = ["9001"])