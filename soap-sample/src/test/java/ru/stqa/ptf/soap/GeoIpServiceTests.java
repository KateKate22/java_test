package ru.stqa.ptf.soap;

import com.lavasoft.GeoIPService;
import org.testng.annotations.Test;

public class GeoIpServiceTests {

  @Test
  public void testMyIp() {
    String ipLocation = new GeoIPService().getGeoIPServiceSoap12().getIpLocation("185.90.100.98");
    //Assert.assertEquals();
  }
}
