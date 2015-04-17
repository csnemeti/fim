// This is a clock designed by PFA Alliance.
// You may use this code any way you want.
// Version 1.0

/** All clocks from a page. */
var pfaAllianceClocks = new Array();

var pfaAllianceAnalogDigitalClockData = {};
var weekday = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];
var monthNames = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
/** 
  * Clock Option constructor.
  */
var pfaAllianceAnalogDigitalClockOptions = {
	width : 357,
	height : 357,
	backgroundColor :  null,
	backgroundImage : "url('images/watch/analog-digital.png')",
	init : initAnalogDigitalClock, 
	renderer : renderAnalogDigitalClock
}
function initAnalogDigitalClock(clockIndex){
	var clock = pfaAllianceClocks[clockIndex];
	
	var nowIs = new Date();
	
	var dayDiv = document.createElement("DIV");
	dayDiv.id = "dayNameDiv" + clockIndex;
	dayDiv.style="position: absolute; top: 100px; left: 80px; width: 205px; height: 35px; z-indez: 10; border: 3px inset black; text-align: center; color: black; font-size: 22px;"
	var dayName = weekday[nowIs.getDay()];
	dayDiv.innerHTML = dayName;  
	clock.clockDiv.appendChild(dayDiv); 
	pfaAllianceAnalogDigitalClockData[dayDiv.id] = dayDiv;
	pfaAllianceAnalogDigitalClockData[dayDiv.id + ".value"] = dayName;

	var timeDiv = document.createElement("DIV");
	timeDiv.id = "timeDiv" + clockIndex;
	timeDiv.style="position: absolute; top: 140px; left: 80px; width: 205px; height: 35px; z-indez: 10; border: 3px inset black; text-align: center; color: black; font-size: 23px;"
	var time = twoDigits(nowIs.getHours()) + " : " + twoDigits(nowIs.getMinutes()) + " : " + twoDigits(nowIs.getSeconds());
	timeDiv.innerHTML = time;
	clock.clockDiv.appendChild(timeDiv); 
	pfaAllianceAnalogDigitalClockData[timeDiv.id] = timeDiv;
	
	dayDiv = document.createElement("DIV");
	dayDiv.id = "dayDiv" + clockIndex;
	dayDiv.style="top: 195px; left: 70px; width: 60px; height: 60px; z-indez: 10; text-align: center; color: black; font-size: 23px;"
	clock.clockDiv.appendChild(dayDiv); 
	pfaAllianceAnalogDigitalClockData[dayDiv.id] = dayDiv;	
	var days = new ProgressBar.Circle(dayDiv, {
	    duration: 200,
	    color: "#000000",
	    trailWidth: 3,
	    strokeWidth: 3,
	    trailColor: "#ddd"
	});
	days.animate(nowIs.getHours() / 24);
	days.setText(nowIs.getDate());
	dayDiv.style.position = "absolute";
	pfaAllianceAnalogDigitalClockData[dayDiv.id + ".value"] = days;
	
	dayDiv = document.createElement("DIV");
	dayDiv.id = "monthDiv" + clockIndex;
	dayDiv.style="top: 240px; left: 151px; width: 60px; height: 60px; z-indez: 10; text-align: center; color: black; font-size: 20px;"
	clock.clockDiv.appendChild(dayDiv); 
	pfaAllianceAnalogDigitalClockData[dayDiv.id] = dayDiv;
	days = new ProgressBar.Circle(dayDiv, {
	    duration: 200,
	    color: "#000000",
	    trailWidth: 3,
	    strokeWidth: 3,
	    trailColor: "#ddd"
	});
	days.animate(nowIs.getDate() / getLastDayOfMonth(nowIs.getMonth(), nowIs.getFullYear()));
	days.setText(monthNames[nowIs.getMonth()]);
	dayDiv.style.position = "absolute";
	pfaAllianceAnalogDigitalClockData[dayDiv.id + ".value"] = days;
	
	dayDiv = document.createElement("DIV");
	dayDiv.id = "yearDiv" + clockIndex;
	dayDiv.style="top: 195px; left: 222px; width: 60px; height: 60px; z-indez: 10;text-align: center; color: black; font-size: 23px;"
	clock.clockDiv.appendChild(dayDiv); 
	pfaAllianceAnalogDigitalClockData[dayDiv.id] = dayDiv;
	days = new ProgressBar.Circle(dayDiv, {
	    duration: 200,
	    color: "#000000",
	    trailWidth: 3,
	    strokeWidth: 3,
	    trailColor: "#ddd"
	});
	days.animate((nowIs.getMonth() + 1) / 12);
	days.setText(nowIs.getFullYear());
	dayDiv.style.position = "absolute";
	pfaAllianceAnalogDigitalClockData[dayDiv.id + ".value"] = days;

	
	console.log("Init", clockIndex);
}
function renderAnalogDigitalClock(clockIndex){
	var nowIs = new Date();
	
	var time = twoDigits(nowIs.getHours()) + " : " + twoDigits(nowIs.getMinutes()) + " : " + twoDigits(nowIs.getSeconds());
	pfaAllianceAnalogDigitalClockData["timeDiv" + clockIndex].innerHTML = time;
}

function twoDigits(number){
	return (number < 10)? "0" + number : "" + number;
}
function getLastDayOfMonth(month, year){
	switch(month){
	case 0 :
	case 2 :
	case 4 :
	case 6 :
	case 7 :
	case 9 :
	case 11 :
		return 31;
	case 3 :
	case 5 :
	case 8 :
	case 10 :
		return 30;
	case 1 :
		// this is not Gregorian calendar but differences will be in 2100  
		return (year % 4 == 0)? 29 : 28;
	}
}

/** 
  * Clock Option constructor.
  */
function pfaAllianceClockOptions(themeName){
	this.theme = "analog-digital",
	this.width = pfaAllianceAnalogDigitalClockOptions.width;
	this.height= pfaAllianceAnalogDigitalClockOptions.height;
	this.backgroundColor = pfaAllianceAnalogDigitalClockOptions.backgroundColor;
	this.backgroundImage = pfaAllianceAnalogDigitalClockOptions.backgroundImage;
	this.renderer = pfaAllianceAnalogDigitalClockOptions.renderer;
	this.init = pfaAllianceAnalogDigitalClockOptions.init;
}

/** 
  * Clock builder.
  * @param divId   - the ID of the DIV / SPAN / etc. that will contain the clock 
  * @param options - the clock options 
  */
function pfaAllianceClock(divId, options){	
	// handle options
	options = new pfaAllianceClockOptions();
	
	// define own data
	this.clockIndex = pfaAllianceClocks.length;
	pfaAllianceClocks[this.clockIndex] = this;

	this.clockDiv = document.getElementById(divId);
	this.timer = null;
	this.option = options;
	this.start = function(){
		this.timer = setInterval(options.renderer, 1000, this.clockIndex); 
	};
	this.stop = function(){
		if(this.timer != null){
			clearInterval(this.timer);
			this.timer = null;
		}
	};
	
	// customize the DIV
	var divStyle = this.clockDiv.style;
	divStyle.width = options.width + "px";
	divStyle.height = options.height + "px";
	divStyle.backgroundColor = options.backgroundColor;
	divStyle.backgroundImage = options.backgroundImage;
	
	options.init(this.clockIndex);
}