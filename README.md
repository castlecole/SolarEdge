# SolarEdge
5KVA Inverter Status Display ST

This is a SmartThings device type handler (henceforth 'DTH') for the Solar Edge solar monitoring web portal.

### Requirements

This DTH directly accesses the Solar Edge portal via your local Inverters Site Address and API code.

### Device View in the SmartThings Mobile Application

The DTH shows the latest data from the device (current power, change since last reading, peak reading for the day) in the top tile, as well as a chart for the data of the last two days (power in blue with units on the left axis, energy in red with units on the right axis; yesterday's data is using fainter colors). Data is only available from the first installation of the DTH so yesteday's data will not be available until the second day using it.

<B>Note:</B> The graph display uses a currently undocumented feature in the SmartThings mobile application which most likely will change in the future. The graph may become temporarily unavailable as server-side changes are beyond my control.
The following screen shots are from the Solar Edge DTH using SmartThings app, with data tiles showing time periods accumulations of generated power:

<img src="https://raw.githubusercontent.com/castlecole/SolarEdge/master/docs/solaredge1.jpg" width="375px" height="667px" />
<img src="https://raw.githubusercontent.com/castlecole/SolarEdge/master/docs/solaredge2.jpg" width="375px" height="667px" />

The tiles below the chart show the total energy production for today, yesterday, the last month, this year and system lifetime in the middle column and the efficiency for the same time spans in the right column. Efficiency is calculated by dividing the total energy output (kWh) by the system size (kW). The efficiency measure allows the outputs of system with different sizes to be compared. The value corresponds to number of hours of full production of the system - expected values depend on location and array orientation.

### Installation

Please see this FAQ in the SmartThings Community for instructions on how to install the device handler to your ST account.
