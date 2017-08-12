

/****** Object:  Table WaMeterData    Script Date: 02/19/2014 15:17:33 ******/






CREATE TABLE WaMeterData(
	ID int  NOT NULL,
	DG_Address varchar(12) NOT NULL,
	Meter_Address varchar(16) NOT NULL,
	Command_Code varchar(50) NOT NULL,
	Read_Time varchar(20) NOT NULL,
	ZY0 decimal(18, 2) NOT NULL,
	Up_Flag int NOT NULL,
 CONSTRAINT PK_WaMeterData PRIMARY KEY 
(
	ID 
)
); 



/****** Object:  Table WaMeter    Script Date: 02/19/2014 15:17:33 ******/






CREATE TABLE WaMeter(
	DG_Address varchar(12) NOT NULL,
	Meter_ID number NOT NULL,
	Meter_Address varchar(16) NOT NULL,
	Meter_Style varchar(50) NOT NULL,
	Has_Data number NOT NULL,
	LastTime varchar(20) NOT NULL
); 



/****** Object:  Table WaDataItem    Script Date: 02/19/2014 15:17:33 ******/






CREATE TABLE WaDataItem(
	Command_Code varchar(6) NOT NULL,
	Data_Code varchar(20) NOT NULL,
	Data_Name varchar(50) NOT NULL,
	Data_Index int NOT NULL
); 



 



/****** Object:  Table LonServer    Script Date: 02/19/2014 15:17:33 ******/






CREATE TABLE LonServer(
	LonServer_ID int  NOT NULL,
	LonServer_No varchar(20) NULL,
	LonServer_IP varchar(50) NULL,
	LonServer_Port varchar(10) NULL,
	LastTime varchar(20) NULL,
	Is_Valid int NULL
); 



/****** EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'LonServer±‡∫≈»Á0015,' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LonServer', @level2type=N'COLUMN',@level2name=N'LonServer_No'

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'»Á192.168.1.15' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LonServer', @level2type=N'COLUMN',@level2name=N'LonServer_IP'
******/
/****** Object:  Table LonMeterData    Script Date: 02/19/2014 15:17:33 ******/






CREATE TABLE LonMeterData(
	ID int  NOT NULL,
	LonMeter_ID int NULL,
	LonMeter_No varchar(30) NULL,
	Read_Time varchar(20) NULL,
	ZYZ decimal(18, 2) NULL,
	ZYA decimal(18, 2) NULL,
	ZYB decimal(18, 2) NULL,
	ZYC decimal(18, 2) NULL,
	PowerZY decimal(18, 2) NULL,
	PowerAY decimal(18, 2) NULL,
	PowerBY decimal(18, 2) NULL,
	PowerCY decimal(18, 2) NULL,
	PowerZW decimal(18, 2) NULL,
	PowerAW decimal(18, 2) NULL,
	PowerBW decimal(18, 2) NULL,
	PowerCW decimal(18, 2) NULL,
	PowerFactorZ decimal(18, 2) NULL,
	PowerFactorA decimal(18, 2) NULL,
	PowerFactorB decimal(18, 2) NULL,
	PowerFactorC decimal(18, 2) NULL,
	VoltageA decimal(18, 2) NULL,
	VoltageB decimal(18, 2) NULL,
	VoltageC decimal(18, 2) NULL,
	CurrentA decimal(18, 2) NULL,
	CurrentB decimal(18, 2) NULL,
	CurrentC decimal(18, 2) NULL,
	Current0 decimal(18, 2) NULL,
	Up_Flag int NULL,
	Alarm varchar(1000) NULL,
 CONSTRAINT PK_LonMeterData PRIMARY KEY 
(
	ID 
)
); 



/****** Object:  Table LonMeter    Script Date: 02/19/2014 15:17:33 ******/






CREATE TABLE LonMeter(
	LonMeter_ID int  NOT NULL,
	LonServer_ID int NULL,
	LonGate_ID int NULL,
	LonMeter_No varchar(30) NULL,
	Meter_Address varchar(20) NULL,
	LastTime varchar(20) NULL,
	PowerZY_UL decimal(18, 4) NULL,
	PowerAY_UL decimal(18, 4) NULL,
	PowerBY_UL decimal(18, 4) NULL,
	PowerCY_UL decimal(18, 4) NULL,
	PowerZW_UL decimal(18, 4) NULL,
	PowerAW_UL decimal(18, 4) NULL,
	PowerBW_UL decimal(18, 4) NULL,
	PowerCW_UL decimal(18, 4) NULL,
	VoltageA_UL decimal(18, 4) NULL,
	VoltageA_LL decimal(18, 4) NULL,
	VoltageB_UL decimal(18, 4) NULL,
	VoltageB_LL decimal(18, 4) NULL,
	VoltageC_UL decimal(18, 4) NULL,
	VoltageC_LL decimal(18, 4) NULL,
	CurrentA_UL decimal(18, 4) NULL,
	CurrentB_UL decimal(18, 4) NULL,
	CurrentC_UL decimal(18, 4) NULL,
	Current0_UL decimal(18, 4) NULL
); 



/****** Object:  Table LonGate    Script Date: 02/19/2014 15:17:33 ******/






CREATE TABLE LonGate(
	LonGate_ID int  NOT NULL,
	LonServer_ID int NULL,
	LonGate_No varchar(20) NULL,
	LonGate_Index int NULL,
	LonMeter_StartAddress varchar(20) NULL,
	LonMeter_Number int NULL,
	Meter_Type varchar(10) NULL
); 



/****** Object:  Table Lamp    Script Date: 02/19/2014 15:17:33 ******/






CREATE TABLE Lamp(
	Lamp_ID int  NOT NULL,
	DG_Address varchar(12) NOT NULL,
	Lamp_Address varchar(4) NOT NULL,
	Port int NOT NULL,
	LastTime varchar(20) NULL,
	Version varchar(50) NULL,
	State1 varchar(10) NULL,
	State2 varchar(10) NULL,
	AccTime1 int NULL,
	AccTime2 int NULL
) ;



/****** Object:  Table DataGate    Script Date: 02/19/2014 15:17:33 ******/






CREATE TABLE DataGate(
	DG_Address varchar(12) NOT NULL,
	DG_Protocol varchar(50) NULL,
	DG_LastCompleteDate varchar(10) NULL,
	DG_AuotRead int NULL
); 



/****** Object:  Table Classroom    Script Date: 02/19/2014 15:17:33 ******/






CREATE TABLE Classroom(
	Classroom_Address varchar(10) NOT NULL,
	DG_Address varchar(12) NOT NULL,
	Gather_PortNo int NOT NULL,
	CurrentLightValue int NULL,
	CurrentPeopleSum int NULL,
	CurrentLineOnSum int NULL,
	LastTime varchar(20) NULL
);



/****** Object:  Table AmMeterDataThreePhase    Script Date: 02/19/2014 15:17:33 ******/






CREATE TABLE AmMeterDataThreePhase(
	ID int  NOT NULL,
	DG_Address varchar(12) NOT NULL,
	Meter_Address varchar(16) NOT NULL,
	Command_Code varchar(50) NOT NULL,
	Read_Time varchar(20) NOT NULL,
	PowerZY decimal(18, 2) NOT NULL,
	PowerAY decimal(18, 2) NOT NULL,
	PowerBY decimal(18, 2) NOT NULL,
	PowerCY decimal(18, 2) NOT NULL,
	PowerZW decimal(18, 2) NOT NULL,
	PowerAW decimal(18, 2) NOT NULL,
	PowerBW decimal(18, 2) NOT NULL,
	PowerCW decimal(18, 2) NOT NULL,
	PowerFactorZ decimal(18, 2) NOT NULL,
	PowerFactorA decimal(18, 2) NOT NULL,
	PowerFactorB decimal(18, 2) NOT NULL,
	PowerFactorC decimal(18, 2) NOT NULL,
	VoltageA decimal(18, 2) NOT NULL,
	VoltageB decimal(18, 2) NOT NULL,
	VoltageC decimal(18, 2) NOT NULL,
	CurrentA decimal(18, 2) NOT NULL,
	CurrentB decimal(18, 2) NOT NULL,
	CurrentC decimal(18, 2) NOT NULL,
	Current0 decimal(18, 2) NOT NULL,
	PowerSZZ decimal(18, 2) NOT NULL,
	PowerSZA decimal(18, 2) NOT NULL,
	PowerSZB decimal(18, 2) NOT NULL,
	PowerSZC decimal(18, 2) NOT NULL,
	Up_Flag int NOT NULL,
	Alarm varchar(1000) NULL,
 CONSTRAINT PK_AmMeterDataThreePhase PRIMARY KEY 
(
	ID 
)
) ;



/****** Object:  Table AmMeterData    Script Date: 02/19/2014 15:17:33 ******/






CREATE TABLE AmMeterData(
	ID int  NOT NULL,
	DG_Address varchar(12) NOT NULL,
	Meter_Address varchar(16) NOT NULL,
	Command_Code varchar(50) NOT NULL,
	Read_Time varchar(20) NOT NULL,
	ZY0 decimal(18, 2) NOT NULL,
	ZY1 decimal(18, 2) NULL,
	ZY2 decimal(18, 2) NULL,
	ZY3 decimal(18, 2) NULL,
	ZY4 decimal(18, 2) NULL,
	Up_Flag int NOT NULL,
 CONSTRAINT PK_AmMeterData PRIMARY KEY 
(
	ID 
)
); 



/****** Object:  Table AmMeter    Script Date: 02/19/2014 15:17:33 ******/






CREATE TABLE AmMeter(
	DG_Address varchar(12) NOT NULL,
	Meter_ID number NOT NULL,
	Meter_Address varchar(16) NOT NULL,
	Meter_Style varchar(50) NOT NULL,
	Has_Data number NOT NULL,
	Has_DataThreePhase number NOT NULL,
	LastTime varchar(20) NOT NULL,
	PowerZY_UL decimal(18, 4) NULL,
	PowerAY_UL decimal(18, 4) NULL,
	PowerBY_UL decimal(18, 4) NULL,
	PowerCY_UL decimal(18, 4) NULL,
	PowerZW_UL decimal(18, 4) NULL,
	PowerAW_UL decimal(18, 4) NULL,
	PowerBW_UL decimal(18, 4) NULL,
	PowerCW_UL decimal(18, 4) NULL,
	VoltageA_UL decimal(18, 4) NULL,
	VoltageA_LL decimal(18, 4) NULL,
	VoltageB_UL decimal(18, 4) NULL,
	VoltageB_LL decimal(18, 4) NULL,
	VoltageC_UL decimal(18, 4) NULL,
	VoltageC_LL decimal(18, 4) NULL,
	CurrentA_UL decimal(18, 4) NULL,
	CurrentB_UL decimal(18, 4) NULL,
	CurrentC_UL decimal(18, 4) NULL,
	Current0_UL decimal(18, 4) NULL
); 



/****** Object:  Table AmDataItem    Script Date: 02/19/2014 15:17:33 ******/






CREATE TABLE AmDataItem(
	Command_Code varchar(6) NOT NULL,
	Data_Code varchar(20) NOT NULL,
	Data_Name varchar(50) NOT NULL,
	Data_Index int NOT NULL
) 
/
/******

/****** Object:  Default DF_AmMeter_Has_Data    Script Date: 02/19/2014 15:17:33 ******/
ALTER TABLE AmMeter ADD  CONSTRAINT DF_AmMeter_Has_Data  DEFAULT (0) FOR Has_Data

/****** Object:  Default DF_AmMeter_Has_DataThreePhase    Script Date: 02/19/2014 15:17:33 ******/
ALTER TABLE AmMeter ADD  CONSTRAINT DF_AmMeter_Has_DataThreePhase  DEFAULT (0) FOR Has_DataThreePhase

/****** Object:  Default DF_LonServer_LonServer_Port    Script Date: 02/19/2014 15:17:33 ******/
ALTER TABLE LonServer ADD  CONSTRAINT DF_LonServer_LonServer_Port  DEFAULT ((502)) FOR LonServer_Port

/****** Object:  Default DF__ssh__id__01142BA1    Script Date: 02/19/2014 15:17:33 ******/
ALTER TABLE ssh ADD  DEFAULT ((0)) FOR id
