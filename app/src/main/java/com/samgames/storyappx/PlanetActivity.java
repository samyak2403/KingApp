package com.samgames.storyappx;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.samgames.storyappx.models.PlanetModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import android.util.Log;
import android.widget.Toast;

public class PlanetActivity extends AppCompatActivity {

    private RecyclerView planetRecyclerView;
    private PlanetAdapter planetAdapter;

    private Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planet);

        planetRecyclerView = findViewById(R.id.planetRecyclerView);
        planetRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<PlanetModel> planetList = createPlanetList();
        planetAdapter = new PlanetAdapter(planetList);
        planetRecyclerView.setAdapter(planetAdapter);

        updateVirtualData();
    }

    private List<PlanetModel> createPlanetList() {
        List<PlanetModel> planets = new ArrayList<>();

        planets.add(new PlanetModel(
                "Mercury",
                "Smallest planet, closest to the Sun",
                "Diameter: 4,879 km\n" +
                        "Distance from Sun: 57.91 million km\n" +
                        "Orbital Period: 88 Earth days\n" +
                        "Surface Temperature: -180°C to 430°C",
                "Interesting Facts:\n" +
                        "1. Closest planet to the Sun\n" +
                        "2. No atmosphere to retain heat\n" +
                        "3. Smallest planet in the solar system\n" +
                        "4. Surface is covered with craters",
                R.raw.mercury,
                R.drawable.mercury
        ));

        planets.add(new PlanetModel(
                "Venus",
                "Hottest planet, similar in size to Earth",
                "Diameter: 12,104 km\n" +
                        "Distance from Sun: 108.2 million km\n" +
                        "Orbital Period: 225 Earth days\n" +
                        "Surface Temperature: 462°C",
                "Interesting Facts:\n" +
                        "1. Rotates backwards compared to other planets\n" +
                        "2. Hottest planet in the solar system\n" +
                        "3. Similar in size to Earth\n" +
                        "4. Covered in volcanic landscapes",
                R.raw.venus,
                R.drawable.venus
        ));

        planets.add(new PlanetModel(
                "Earth",
                "Our home planet, the only known planet with life",
                "Diameter: 12,742 km\n" +
                        "Distance from Sun: 149.6 million km\n" +
                        "Orbital Period: 365.25 days\n" +
                        "Surface Temperature: -89°C to 56°C",
                "Interesting Facts:\n" +
                        "1. Only known planet with liquid water\n" +
                        "2. Has a protective magnetic field\n" +
                        "3. Supports a wide variety of life\n" +
                        "4. 71% of surface is covered by water",
                R.raw.earth,
                R.drawable.earth
        ));

        planets.add(new PlanetModel(
                "Mars",
                "The Red Planet, potential for future human exploration",
                "Diameter: 6,779 km\n" +
                        "Distance from Sun: 227.9 million km\n" +
                        "Orbital Period: 687 Earth days\n" +
                        "Surface Temperature: -140°C to 20°C",
                "Interesting Facts:\n" +
                        "1. Known as the Red Planet\n" +
                        "2. Has the largest volcano in the solar system\n" +
                        "3. Potential for past or present microbial life\n" +
                        "4. Has two small moons: Phobos and Deimos",
                R.raw.mars,
                R.drawable.mars
        ));

        planets.add(new PlanetModel(
                "Jupiter",
                "Largest planet in our solar system",
                "Diameter: 139,820 km\n" +
                        "Distance from Sun: 778.5 million km\n" +
                        "Orbital Period: 11.86 Earth years\n" +
                        "Surface Temperature: -108°C",
                "Interesting Facts:\n" +
                        "1. Largest planet in the solar system\n" +
                        "2. Has the Great Red Spot\n" +
                        "3. 79 known moons\n" +
                        "4. Composed mainly of hydrogen and helium",
                R.raw.jupiter,
                R.drawable.jupiter
        ));

        planets.add(new PlanetModel(
                "Saturn",
                "Known for its spectacular ring system",
                "Diameter: 116,460 km\n" +
                        "Distance from Sun: 1.43 billion km\n" +
                        "Orbital Period: 29.46 Earth years\n" +
                        "Surface Temperature: -178°C",
                "Interesting Facts:\n" +
                        "1. Most famous for its ring system\n" +
                        "2. Least dense planet in the solar system\n" +
                        "3. 82 known moons\n" +
                        "4. Rings are made of ice and rock",
                R.raw.moon,
                R.drawable.saturn
        ));

        planets.add(new PlanetModel(
                "Uranus",
                "The tilted planet, rotating on its side",
                "Diameter: 50,724 km\n" +
                        "Distance from Sun: 2.87 billion km\n" +
                        "Orbital Period: 84 Earth years\n" +
                        "Surface Temperature: -224°C",
                "Interesting Facts:\n" +
                        "1. Tilted on its side at 98 degrees\n" +
                        "2. Appears to roll on its side\n" +
                        "3. 27 known moons\n" +
                        "4. Appears blue-green due to methane",
                R.raw.moon,
                R.drawable.uranus
        ));

        planets.add(new PlanetModel(
                "Neptune",
                "The windiest planet in the solar system",
                "Diameter: 49,244 km\n" +
                        "Distance from Sun: 4.5 billion km\n" +
                        "Orbital Period: 164.8 Earth years\n" +
                        "Surface Temperature: -214°C",
                "Interesting Facts:\n" +
                        "1. Windiest planet in the solar system\n" +
                        "2. Winds can reach up to 1,500 mph\n" +
                        "3. 14 known moons\n" +
                        "4. Deep blue color due to methane",
                R.raw.moon,
                R.drawable.neptune
        ));

        return planets;
    }

    private void updateVirtualData() {
        updateBuildInfo();
        updateTelephonyInfo();
        updateBatteryLevel();
        updateNetworkInfo();
        updateDisplayInfo();
        updateMacAddress();
        updateBluetoothAddress();
        updateActiveUser();
        updateUserEngagement();
    }

    private void updateBuildInfo() {
        String model = "Model_" + random.nextInt(100);
        String brand = "Brand_" + random.nextInt(50);
        String manufacturer = "Manufacturer_" + random.nextInt(30);
        String releaseVersion = "Release_" + random.nextInt(10);
        String sdkVersion = "SDK_" + random.nextInt(100);
        
        DataValueChanged("Build Info", String.format(Locale.US, 
            "Model:%s, Brand:%s, Manufacturer:%s, Release:%s, SDK:%s", 
            model, brand, manufacturer, releaseVersion, sdkVersion
        ));
    }

    private void updateTelephonyInfo() {
        String deviceId = "DEV" + random.nextInt(1000000);
        String subscriberId = "Sub" + random.nextInt(1000000);
        String simSerialNumber = "SIM" + random.nextInt(5000);
        
        DataValueChanged("Telephony Info", String.format(Locale.US, 
            "Device ID:%s, Subscriber ID:%s, SIM Serial:%s", 
            deviceId, subscriberId, simSerialNumber
        ));
    }

    private void updateBatteryLevel() {
        int batteryLevel = random.nextInt(101);
        DataValueChanged("Battery Level", String.format(Locale.US, "Battery Level: %d%%", batteryLevel));
    }

    private void updateNetworkInfo() {
        String networkType = "Network_" + random.nextInt(10);
        String networkOperator = "Operator_" + random.nextInt(20);
        DataValueChanged("Network Info", String.format(Locale.US, 
            "Network Type:%s, Network Operator:%s", 
            networkType, networkOperator
        ));
    }

    private void updateDisplayInfo() {
        int screenWidth = random.nextInt(1080) + 800;
        int screenHeight = random.nextInt(1920) + 600;
        DataValueChanged("Display Info", String.format(Locale.US, 
            "Screen Width:%d, Screen Height:%d", screenWidth, screenHeight
        ));
    }

    private void updateMacAddress() {
        byte[] macAddr = new byte[6];
        random.nextBytes(macAddr);
        
        // Ensure the first byte is not a multicast address
        macAddr[0] = (byte) (macAddr[0] & (byte) 0xfe);
        
        // Convert to a formatted MAC address string
        StringBuilder macAddress = new StringBuilder();
        for (int i = 0; i < macAddr.length; i++) {
            macAddress.append(String.format(Locale.US, "%02X%s", macAddr[i], 
                (i < macAddr.length - 1) ? ":" : ""));
        }
        
        DataValueChanged("MAC Address", "MAC Address: " + macAddress.toString());
    }

    private void updateBluetoothAddress() {
        byte[] macAddr = new byte[6];
        random.nextBytes(macAddr);
        
        // Ensure the first byte is not a multicast address
        macAddr[0] = (byte) (macAddr[0] & (byte) 0xfe);
        
        // Convert to a formatted Bluetooth MAC address string
        StringBuilder bluetoothAddress = new StringBuilder();
        for (int i = 0; i < macAddr.length; i++) {
            bluetoothAddress.append(String.format(Locale.US, "%02X%s", macAddr[i], 
                (i < macAddr.length - 1) ? ":" : ""));
        }
        
        DataValueChanged("Bluetooth Address", "Bluetooth Address: " + bluetoothAddress.toString());
    }

    private void updateActiveUser() {
        int activeUsers = random.nextInt(5000) + 100;
        DataValueChanged("Active Users", String.format(Locale.US, "Active Users: %d", activeUsers));
    }

    private void updateUserEngagement() {
        int userEngagement = random.nextInt(100);
        DataValueChanged("User Engagement", String.format(Locale.US, "User Engagement: %d%%", userEngagement));
    }

    private void DataValueChanged(String category, String data) {
        Log.d("PlanetActivity", category + ": " + data);
    }
}