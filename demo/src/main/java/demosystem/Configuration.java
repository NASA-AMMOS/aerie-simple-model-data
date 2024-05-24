package demosystem;

import static gov.nasa.jpl.aerie.merlin.framework.annotations.Export.Template;

public record Configuration(double initialLanderMaxVolume, double initialLanderDatarate,
                            double roverMaxVolume, double roverDatarate,
                            double baseStationMaxVolume, double baseStationDatarate) {
  public static @Template Configuration defaultConfiguration() {
    return new Configuration(
      1e10, 1e6,  // 10 Gb, 1 Mbps
      1e9, 1e6,  // 1 Gb, 1 Mbps
      3e9, 1e8); // 3 Gb, 100 Mbps
  }
}
