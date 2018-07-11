/**
 *	SolarEdge Solar System
 *
 *	Copyright 2016 Andreas Amann
 *
 *	Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *	in compliance with the License. You may obtain a copy of the License at:
 *
 *			http://www.apache.org/licenses/LICENSE-2.0
 *
 *	Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *	on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *	for the specific language governing permissions and limitations under the License.
 *
 */

def version() {
	return "0.2.2 (20170313)\n© 2016–2017 Andreas Amann"
}

preferences {
	input("confSiteID", "number", title: "Site ID", required: true, displayDuringSetup: true)
	input("confApiKey", "text", title: "API Key (get this from your Solar PV installer)", required: true, displayDuringSetup: true)
	input("pollingInterval", "number", title:"Polling Interval (min)",
		defaultValue:"5", range: "2..59", required: true, displayDuringSetup: true)
	input(title: "", description: "Total System Size (W)\n\nRated maximum power in Watts for the system", type: "paragraph", element: "paragraph", displayDuringSetup: true)
	input("confSystemSize", "number", title:"", required: true, displayDuringSetup: true)
	input(title:"", description: "Version: ${version()}", type: "paragraph", element: "paragraph")
}

metadata {
	definition (name: "SolarEdge", namespace: "castlecole", author: "Andreas Amann") {
		capability "Sensor"
		capability "Power Meter"
		capability "Energy Meter"
		capability "Refresh"
		capability "Polling"
		capability "Health Check"

		attribute "energy_str", "string"
		attribute "energy_yesterday", "string"
		attribute "energy_lastMonth", "string"
		attribute "energy_lastYear", "string"
		attribute "energy_life", "string"
		attribute "power_details", "string"

		attribute "efficiency", "string"
		attribute "efficiency_yesterday", "string"
		attribute "efficiency_lastMonth", "string"
		attribute "efficiency_lastYear", "string"
	}

	simulator {
		// TODO: define status and reply messages here
	}

	tiles(scale: 2) {
		// this tile is used for display in device list (to get correct colorization)
		valueTile( "power", "device.power") {
			state("power", label: '${currentValue}W', unit: "W",
				icon: "https://raw.githubusercontent.com/castlecole/customdevices/master/Solar-small.png",
				backgroundColors: [
					[value: 0, color: "#bc2323"],
					[value: 3000, color: "#1e9cbb"],
					[value: 6000, color: "#90d2a7"]
				]
			 )
		}

		// this tile is used only to provide an icon in the recent events list
		valueTile("energy", "device.energy") {
			state("energy", label: '${currentValue}', unit: "kWh")
		}

		// the following tiles are used for display in the device handler
		multiAttributeTile(name:"SolarMulti", type:"generic", width:6, height:4) {
			tileAttribute("device.power", key: "PRIMARY_CONTROL") {
			attributeState("power", label: '${currentValue}W',
				icon: "https://raw.githubusercontent.com/castlecole/customdevices/master/Solar.png",
						unit: "W",
						backgroundColors: [
							[value: 0, color: "#bc2323"],
							[value: 2500, color: "#1e9cbb"],
							[value: 4000, color: "#90d2a7"]
						])
			}

			tileAttribute("device.power_details", key: "SECONDARY_CONTROL") {
				attributeState("power_details",
					label: '${currentValue}')
			}
		}
		standardTile(
			"today",
			"today",
			width: 2,
			height: 2) {
				state("default",
				        icon:"https://raw.githubusercontent.com/castlecole/customdevices/master/time_today2.png",
						label:"TODAY" )
		}
		valueTile(
			"energy_str",
			"device.energy_str",
			width: 2,
			height: 2,
			decoration: "flat",
			wordWrap: true) {
				state("energy_str",
					label: '${currentValue}')
		}
		valueTile(
			"efficiency",
			"device.efficiency",
			width: 2,
			height: 2) {
				state("efficiency",
					label: '${currentValue}',
					backgroundColors: [
						[value: 0, color: "#bc2323"],
						[value: 2, color: "#d04e00"],
						[value: 4, color: "#f1d801"],
						[value: 5, color: "#90d2a7"],
						[value: 6, color: "#44b621"]
					])
		}
		standardTile(
			"yesterday",
			"yesterday",
			width: 2,
			height: 2) {
				state("default",
				       icon:"https://raw.githubusercontent.com/castlecole/customdevices/master/time_yesterday2.png",
				       label:"YESTERDAY")
		}
		valueTile(
			"energy_yesterday",
			"device.energy_yesterday",
			width: 2,
			height: 2,
			decoration: "flat",
			wordWrap: true) {
				state("energy_yesterday",
					label: '${currentValue}')
		}
		valueTile(
			"efficiency_yesterday",
			"device.efficiency_yesterday",
			width: 2,
			height: 2) {
				state("efficiency_yesterday",
					label: '${currentValue}',
					backgroundColors: [
						[value: 0, color: "#bc2323"],
						[value: 2, color: "#d04e00"],
						[value: 4, color: "#f1d801"],
						[value: 5, color: "#90d2a7"],
						[value: 6, color: "#44b621"]
					])
		}
		standardTile(
			"lastMonth",
			"lastMonth",
			width: 2,
			height: 2) {
				state("default",
				    icon:"https://raw.githubusercontent.com/castlecole/customdevices/master/time_month2.png",
					label:"THIS MONTH")
		}
		valueTile(
			"energy_lastMonth",
			"device.energy_lastMonth",
			width: 2,
			height: 2,
			decoration: "flat",
			wordWrap: true) {
				state("energy_lastMonth",
					label: '${currentValue}')
		}
		valueTile(
			"efficiency_lastMonth",
			"device.efficiency_lastMonth",
			width: 2,
			height: 2) {
				state("efficiency_lastMonth",
					label: '${currentValue}',
					backgroundColors: [
						[value: 0, color: "#bc2323"],
						[value: 2, color: "#d04e00"],
						[value: 4, color: "#f1d801"],
						[value: 5, color: "#90d2a7"],
						[value: 6, color: "#44b621"]
					])
		}
		standardTile(
			"lastYear",
			"lastYear",
			width: 2,
			height: 2) {
				state("default",
			        icon:"https://raw.githubusercontent.com/castlecole/customdevices/master/time_year2.png",
					label:"THIS YEAR")
		}
		valueTile(
			"energy_lastYear",
			"device.energy_lastYear",
			width: 2,
			height: 2,
			decoration: "flat",
			wordWrap: true) {
				state("energy_lastYear",
					label: '${currentValue}')
		}
		valueTile(
			"efficiency_lastYear",
			"device.efficiency_lastYear",
			width: 2,
			height: 2) {
				state("efficiency_lastYear",
					label: '${currentValue}',
					backgroundColors: [
						[value: 0, color: "#bc2323"],
						[value: 2, color: "#d04e00"],
						[value: 4, color: "#f1d801"],
						[value: 5, color: "#90d2a7"],
						[value: 6, color: "#44b621"]
					])
		}
		standardTile(
			"lifetime",
			"lifetime",
			width: 2,
			height: 2) {
				state("default",
				      icon:"https://raw.githubusercontent.com/castlecole/customdevices/master/time_life2.png",
				      label:"SINCE INSTALL")
		}
		valueTile(
			"energy_life",
			"device.energy_life",
			width: 2,
			height: 2,
			decoration: "flat",
			wordWrap: true) {
				state("energy_life",
					label: '${currentValue}')
		}
		standardTile(
			"refresh",
			"device.refresh",
			inactiveLabel: false,
			decoration: "flat",
			width: 2,
			height: 2) {
				state("default", action:"polling.poll",
					icon: "https://raw.githubusercontent.com/castlecole/customdevices/master/refresh.png")
		}

		htmlTile(name:"graphHTML", action: "getGraphHTML",
			refreshInterval: 1,
			width:6,
			height:6,
			whitelist: ["www.gstatic.com"])

		main "power"

		details(["SolarMulti", "graphHTML", "today", "energy_str", "efficiency", "yesterday", "energy_yesterday", "efficiency_yesterday", "lastMonth", "energy_lastMonth", "efficiency_lastMonth", "lastYear", "energy_lastYear", "efficiency_lastYear", "lifetime", "energy_life", "refresh"])

	}


}


mappings {
	path("/getGraphHTML") {action: [GET: "getGraphHTML"]}
}


def poll() {
	pullData()
}


def refresh() {
	pullData()
}

def updated() {
	if (!state.updatedLastRanAt || now() >= state.updatedLastRanAt + 2000) {
		state.updatedLastRanAt = now()
		log.trace("$device.displayName - updated with settings: ${settings.inspect()}")
		device.setDeviceNetworkId(settings.confSiteID.toString())

		// Notify health check about this device with timeout interval equal to 5 failed update requests
		// (add 30 seconds so we don't collide with the 5th request in case that succeeds)
		def healthCheckInterval = 5 * settings.pollingInterval.toInteger() * 60 + 30
		sendEvent(name: "checkInterval", value: healthCheckInterval, data: [protocol: "cloud", hubHardwareId: device.hub.hardwareID], displayed: false)
		pullData()
		startPoll()
	} else {
		log.trace("$device.displayName - updated() ran within the last 2 seconds - skipping")
	}
}


def ping() {
	log.trace("$device.displayName - checking device health…")
	pullData()
}

def startPoll() {
	unschedule()

	// Schedule polling based on preference setting
	def sec = Math.round(Math.floor(Math.random() * 60))
	def min = Math.round(Math.floor(Math.random() * settings.pollingInterval.toInteger()))
	def cron = "${sec} ${min}/${settings.pollingInterval.toInteger()} * * * ?" // every N min
	log.trace("$device.displayName - startPoll: schedule('$cron', pullData)")
	schedule(cron, pullData)
}


def pullData() {

	log.debug "Requesting latest data from SolarEdge…"

	def cmd = [uri: "https://monitoringapi.solaredge.com/site/${settings.confSiteID}/overview?api_key=${settings.confApiKey}"]
	
	try {
    	
	// New Json call to replace original httpGet call.
	httpPut(cmd) { resp ->

//		resp.headers.each {
//            	    log.debug "${it.name} : ${it.value}"
//        	}
//        	log.debug "response contentType: ${resp.contentType}"
//    		}
	
//	def cmd = "https://monitoringapi.solaredge.com/site/${settings.confSiteID}/overview?api_key=${settings.confApiKey}"
//	httpGet(cmd) {resp ->
		def data = resp.data
		if (data == state.lastData) 
			log.debug "No new data"
			sendEvent(name: 'lastUpdate', value: new Date(), displayed: false) // dummy event for health check
			return null
		}
		state.lastData = data
		log.debug "New data: ${data}"
		def energyToday = (data.overview.lastDayData.energy/1000).toFloat()
		def energyLastMonth = (data.overview.lastMonthData.energy/1000).toFloat()
		def energyLastYear = (data.overview.lastYearData.energy/1000000).toFloat()
		def energyLife = (data.overview.lifeTimeData.energy/1000000).toFloat()
    		def currentPower = data.overview.currentPower.power.toInteger()
		def todayDay = new Date().format("dd",location.timeZone)
		def powerTable = state.powerTable
		def energyTable = state.energyTable
		if (!state.today || state.today != todayDay) {
			state.peakpower = currentPower
			state.today = todayDay
			state.powerTableYesterday = powerTable
			state.energyTableYesterday = energyTable
      			powerTable = powerTable ? [] : null
			energyTable = energyTable ? [] : null
			state.lastPower = 0
			sendEvent(name: 'energy_yesterday', value: device.currentState("energy_str")?.value, displayed: false)
			sendEvent(name: 'efficiency_yesterday', value: device.currentState("efficiency")?.value, displayed: false)
		}

		def previousPower = state.lastPower != null ? state.lastPower : currentPower
		def powerChange = currentPower - previousPower
		state.lastPower = currentPower

		if (state.peakpower <= currentPower) {
			state.peakpower = currentPower
			state.peakpercentage = (100*state.peakpower/settings.confSystemSize).toFloat()
		}

		def events = []
		events << createEvent(name: 'power_details', value: ("(" + String.format("%+,d", powerChange.toInteger()) + "W) — Today's Peak: " + String.format("%,d", state.peakpower.toInteger()) + "W (" + String.format("%.1f", state.peakpercentage) + "%)"), displayed: false)
		events << createEvent(name: 'energy_lastMonth', value: String.format("%,#.3f", energyLastMonth) + "\nkWh", displayed: false)
		events << createEvent(name: 'energy_lastYear', value: String.format("%,#.3f", energyLastYear) + "\nMWh", displayed: false)
		events << createEvent(name: 'energy_life', value: String.format("%,#.3f", energyLife) + "\nMWh", displayed: false)
		def efficiencyToday = (1000*energyToday/settings.confSystemSize).toFloat()
		events << createEvent(name: 'efficiency', value: String.format("%#.3f", efficiencyToday) + "\nkWh/kW", displayed: false)
		def efficiencyLastMonth = (1000/todayDay.toInteger()*energyLastMonth/settings.confSystemSize).toFloat()
		events << createEvent(name: 'efficiency_lastMonth', value: String.format("%#.3f", efficiencyLastMonth) + "\nkWh/kW", displayed: false)
		def dayInYear = new Date().format("D", location.timeZone).toInteger()
		def efficiencyLastYear = (1000000/dayInYear*energyLastYear/settings.confSystemSize).toFloat()
		events << createEvent(name: 'efficiency_lastYear', value: String.format("%#.3f", efficiencyLastYear) + "\nkWh/kW", displayed: false)
		events << createEvent(name: 'energy_str', value: String.format("%,#.3f", energyToday) + "\nkWh", displayed: false)
		events << createEvent(name: 'energy', value: energyToday, unit: "kWh", descriptionText: "Energy is " + String.format("%,#.3f", energyToday) + "kWh\n(Efficiency: " + String.format("%#.3f", efficiencyToday) + "kWh/kW)")
		events << createEvent(name: 'power', value: currentPower, unit: "W", descriptionText: "Power is " + String.format("%,d", currentPower.toInteger()) + "W (" + String.format("%#.1f", 100*currentPower/settings.confSystemSize) + "%)\n(" + String.format("%+,d", powerChange.toInteger()) + "W since last reading)")

		// get power data for yesterday and today so we can create a graph
		if (state.powerTableYesterday == null || state.energyTableYesterday == null || powerTable == null || energyTable == null) {
			def startOfToday = timeToday("00:00", location.timeZone)
			def newValues
			if (state.powerTableYesterday == null || state.energyTableYesterday == null) {
				log.trace "Querying DB for yesterday's data…"
				def dataTable = []
				def powerData = device.statesBetween("power", startOfToday - 1, startOfToday, [max: 288]) // 24h in 5min intervals should be more than sufficient…
				if (powerData.size()) {

					// work around a bug where the platform would return less than the requested number of events (as June 2016, only 50 events are returned)
					while ((newValues = device.statesBetween("power", startOfToday - 1, powerData.last().date, [max: 288])).size()) {
						powerData += newValues
					}
					powerData.reverse().each() {
						dataTable.add([it.date.format("H", location.timeZone),it.date.format("m", location.timeZone),it.integerValue])
					}
				}

				state.powerTableYesterday = dataTable
				dataTable = []
				def energyData = device.statesBetween("energy", startOfToday - 1, startOfToday, [max: 288])
				if (energyData.size()) {
					while ((newValues = device.statesBetween("energy", startOfToday - 1, energyData.last().date, [max: 288])).size()) {
						energyData += newValues
					}

					// we drop the first point after midnight (0 energy) in order to have the graph scale correctly
					energyData.reverse().drop(1).each() {
						dataTable.add([it.date.format("H", location.timeZone),it.date.format("m", location.timeZone),it.floatValue])
					}
				}
				state.energyTableYesterday = dataTable
			}
			if (powerTable == null || energyTable == null) {
				log.trace "Querying DB for today's data…"
				powerTable = []
				def powerData = device.statesSince("power", startOfToday, [max: 288])
				if (powerData.size()) {
					while ((newValues = device.statesBetween("power", startOfToday, powerData.last().date, [max: 288])).size()) {
						powerData += newValues
					}
					powerData.reverse().each() {
						powerTable.add([it.date.format("H", location.timeZone),it.date.format("m", location.timeZone),it.integerValue])
					}
				}
				energyTable = []
				def energyData = device.statesSince("energy", startOfToday, [max: 288])
				if (energyData.size()) {
					while ((newValues = device.statesBetween("energy", startOfToday, energyData.last().date, [max: 288])).size()) {
						energyData += newValues
					}
					 energyData.reverse().drop(1).each() {
						energyTable.add([it.date.format("H", location.timeZone),it.date.format("m", location.timeZone),it.floatValue])
					}
				}
			}
		}


		// add latest power & energy readings for the graph
		if (currentPower > 0 || powerTable.size() != 0) {
			def newDate = new Date()
			powerTable.add([newDate.format("H", location.timeZone),newDate.format("m", location.timeZone),currentPower])
			energyTable.add([newDate.format("H", location.timeZone),newDate.format("m", location.timeZone),energyToday])
		}
		state.powerTable = powerTable
		state.energyTable = energyTable
		events.each() {
			sendEvent(it)
		}

	} catch (e) {
    		log.debug "Something went badly wrong: $e"
	}

}

String getDataString(Integer seriesIndex) {
	def dataString = ""
	def dataTable = []
	switch (seriesIndex) {
		case 1:
			dataTable = state.energyTableYesterday
			break
		case 2:
			dataTable = state.powerTableYesterday
			break
		case 3:
			dataTable = state.energyTable
			break
		case 4:
			dataTable = state.powerTable
			break
	}
	dataTable.each() {
		def dataArray = [[it[0],it[1],0],null,null,null,null]
		dataArray[seriesIndex] = it[2]
		dataString += dataArray.toString() + ","
	}
	return dataString
}

def parse(String message) {
}


def getStartTime() {
	def startTime = 24
	if (state.powerTable.size() > 0) {
		startTime = state.powerTable.min{it[0].toInteger()}[0].toInteger()
	}

	if (state.powerTableYesterday.size() > 0) {
		startTime = Math.min(startTime, state.powerTableYesterday.min{it[0].toInteger()}[0].toInteger())
	}
	return startTime
}

def getGraphHTML() {
	def html = """
		<!DOCTYPE html>
			<html>
				<head>
					<meta http-equiv="cache-control" content="max-age=0"/>
					<meta http-equiv="cache-control" content="no-cache"/>
					<meta http-equiv="expires" content="0"/>
					<meta http-equiv="expires" content="Tue, 01 Jan 1980 1:00:00 GMT"/>
					<meta http-equiv="pragma" content="no-cache"/>
					<meta name="viewport" content="width = device-width">
					<meta name="viewport" content="initial-scale = 1.0, user-scalable=no">
					<style type="text/css">body,div {margin:0;padding:0}</style>
					<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
					<script type="text/javascript">
            				google.charts.load('current', {packages: ['corechart']});
					google.charts.setOnLoadCallback(drawGraph);
						function drawGraph() {
							var data = new google.visualization.DataTable();
							data.addColumn('timeofday', 'Time');
							data.addColumn('number', 'Energy (Yesterday)');
							data.addColumn('number', 'Power (Yesterday)');
							data.addColumn('number', 'Energy (Today)');
							data.addColumn('number', 'Power (Today)');
							data.addRows([
								${getDataString(1)}
								${getDataString(2)}
								${getDataString(3)}
								${getDataString(4)}
							]);
							var options = {
								fontName: 'San Francisco, Roboto, Arial',
								height: 240,
								hAxis: {
									format: 'H:mm',
									minValue: [${getStartTime()},0,0],
									slantedText: false
								},
								series: {
									0: {targetAxisIndex: 1, color: '#FFC2C2', lineWidth: 1},
									1: {targetAxisIndex: 0, color: '#D1DFFF', lineWidth: 1},
									2: {targetAxisIndex: 1, color: '#FF0000'},
									3: {targetAxisIndex: 0, color: '#004CFF'}
								},
								vAxes: {
									0: {
										title: 'Power (W)',
										format: 'decimal',
										textStyle: {color: '#004CFF'},
										titleTextStyle: {color: '#004CFF'}
									},
									1: {
										title: 'Energy (kWh)',
										format: 'decimal',
										textStyle: {color: '#FF0000'},
										titleTextStyle: {color: '#FF0000'}
									}
								},
								legend: {
									position: 'none'
								},
								chartArea: {
									width: '72%',
									height: '85%'
								}
							};
							var chart = new google.visualization.AreaChart(document.getElementById('chart_div'));
							chart.draw(data, options);
						}
					</script>
				</head>
				<body>
					<div style="font:arial;text-align:center;border:1px solid black">Solar Output Today</div>
					<div id="chart_div" style="border:1px solid black"></div>
				</body>
			</html>
		"""
	render contentType: "text/html", data: html, status: 200
}
