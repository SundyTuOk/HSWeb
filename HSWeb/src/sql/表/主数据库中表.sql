

/****** Object:  Table WaterMeterDatas    Script Date: 02/19/2014 15:15:50 ******/




CREATE TABLE WaterMeterDatas(
	WaterMeter_ID int NOT NULL,
	ValueTime date NOT NULL,
	ZValueZY decimal(12, 2) NOT NULL,
 CONSTRAINT PK_WaterMeterDatas PRIMARY KEY 
(
	WaterMeter_ID ,
	ValueTime 
)
); 

/****** Object:  Table WaterMeter    Script Date: 02/19/2014 15:15:50 ******/






CREATE TABLE WaterMeter(
	WaterMeter_ID int NOT NULL,
	WaterMeter_Point int NULL,
	Area_ID int NULL,
	Architecture_ID int NULL,
	Parent_ID int NULL,
	Gather_ID int NULL,
	WaterMeter_Num varchar(20) NULL,
	WaterMeter_Name varchar(50) NULL,
	WaterMeter_Password varchar(8) NULL,
	WaterMeter_485Address varchar(30) NULL,
	Maker varchar(50) NULL,
	MakerCode varchar(50) NULL,
	AssetNo varchar(50) NULL,
	IsSupply int NOT NULL,
	ZValue decimal(18, 2) NOT NULL,
	UseAmStyle int NULL,
	ConsumerNum varchar(20) NULL,
	ConsumerName varchar(50) NULL,
	ConsumerPhone varchar(20) NULL,
	ConsumerAddress varchar(200) NULL,
	IsImportantUser int NULL,
	IsCountMeter int NULL,
	Partment int NULL,
	Floor int NULL,
	MeteStyle_ID int NULL,
	isComputation int NULL,
	Price_ID int NULL,
	WaterMeter_Stat int NULL,
	IsOffAlarm int NULL,
	CostCheck int NULL,
	StandByCheck int NULL,
	Xiuzheng decimal(18, 2) NULL,
	LastTime date NULL,
	WLeakageCheck int NOT NULL,
	WLeakageValue decimal(18, 2) NULL,
	DataFrom int NULL,
	BeiLv decimal(10, 2) NULL,
 CONSTRAINT PK_WaterMeter PRIMARY KEY 
(
	WaterMeter_ID 
)
); 



/****** Object:  Table WaterMatchModel    Script Date: 02/19/2014 15:15:50 ******/




CREATE TABLE WaterMatchModel(
	WaterMeter_ID int NOT NULL,
	H0 decimal(18, 2) NULL,
	H1 decimal(18, 2) NULL,
	H2 decimal(18, 2) NULL,
	H3 decimal(18, 2) NULL,
	H4 decimal(18, 2) NULL,
	H5 decimal(18, 2) NULL,
	H6 decimal(18, 2) NULL,
	H7 decimal(18, 2) NULL,
	H8 decimal(18, 2) NULL,
	H9 decimal(18, 2) NULL,
	H10 decimal(18, 2) NULL,
	H11 decimal(18, 2) NULL,
	H12 decimal(18, 2) NULL,
	H13 decimal(18, 2) NULL,
	H14 decimal(18, 2) NULL,
	H15 decimal(18, 2) NULL,
	H16 decimal(18, 2) NULL,
	H17 decimal(18, 2) NULL,
	H18 decimal(18, 2) NULL,
	H19 decimal(18, 2) NULL,
	H20 decimal(18, 2) NULL,
	H21 decimal(18, 2) NULL,
	H22 decimal(18, 2) NULL,
	H23 decimal(18, 2) NULL,
	LastCheckTime date NULL,
 CONSTRAINT PK_WaterMatchModel PRIMARY KEY 
(
	WaterMeter_ID 
)
) ;

/****** Object:  Table WaterMaintenance    Script Date: 02/19/2014 15:15:50 ******/






CREATE TABLE WaterMaintenance(
	Maint_ID int NOT NULL,
	WaterMeter_ID int NULL,
	MaintTime date NULL,
	MaintRemark varchar(2000) NULL,
	MaintMan varchar(50) NULL,
 CONSTRAINT PK_WaterMaintenance PRIMARY KEY 
(
	Maint_ID 
)
); 



/****** Object:  Table WaterLeak    Script Date: 02/19/2014 15:15:50 ******/




CREATE TABLE WaterLeak(
	WaterLeak_ID decimal(18, 0) NOT NULL,
	SelectYear int NOT NULL,
	SelectMonth int NOT NULL,
	SelectDay int NOT NULL,
	WaterLeak_Style int NULL,
	WaterMeter_ID int NULL,
	WaterLeak_ZGross decimal(18, 2) NULL,
	WaterLeak_SumGross decimal(18, 2) NULL,
	WaterLeak_CGross decimal(18, 2) NULL,
	WaterLeak_Percent decimal(6, 4) NULL,
	insertTime date NULL,
 CONSTRAINT PK_WaterLeak PRIMARY KEY 
(
	WaterLeak_ID 
)
); 

/******
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'WaterLeak表中异常类型0零用水，1总分表用量差超过波动上限，2漏抄，3分表和大于总表用量，4时段监测异常，5用量突增，' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'WaterLeak', @level2type=N'COLUMN',@level2name=N'WaterLeak_Style'

/****** Object:  Table WaterBaojing    Script Date: 02/19/2014 15:15:50 ******/






CREATE TABLE WaterBaojing(
	WaterBaojing_ID int NOT NULL,
	WaterBaojing_Style int NULL,
	WaterBaojing_Time date NULL,
	WaterMeter_ID int NULL,
	WaterBaojing_Remark varchar(200) NULL,
	WaterBaojing_SendSMS int NULL,
	inserttime date NULL
); 



/******
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'0监控时段超出最大用量差值有漏水或为关水阀嫌疑' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'WaterBaojing', @level2type=N'COLUMN',@level2name=N'WaterBaojing_Style'

/****** Object:  Table Users_Roles    Script Date: 02/19/2014 15:15:50 ******/




CREATE TABLE Users_Roles(
	Users_ID numeric(18, 0) NOT NULL,
	Roles_ID numeric(18, 0) NOT NULL,
 CONSTRAINT PK_USERS_ROLES PRIMARY KEY 
(
	Users_ID ,
	Roles_ID 
)
); 

/****** Object:  Table Users    Script Date: 02/19/2014 15:15:50 ******/






CREATE TABLE Users(
	Users_ID numeric(18, 0) NOT NULL,
	Area_ID numeric(18, 0) NULL,
	Users_Num varchar(20) NOT NULL,
	Users_Name varchar(250) NOT NULL,
	Users_LoginName varchar(20) NULL,
	Users_Password varchar(50) NULL,
	Users_Gender varchar(5) NULL,
	Users_Birth date NULL,
	Users_Photo varchar(50) NULL,
	Users_Department int NULL,
	Users_Phone varchar(20) NULL,
	Users_Phone1 varchar(20) NULL,
	Users_Phone2 varchar(20) NULL,
	Users_HomeAddress varchar(250) NULL,
	Users_LastTime date NULL,
	Users_RegTime date NULL,
	UserState_ID int NULL,
	Users_Remark varchar(250) NULL,
	OnLinere int DEFAULT 0 NULL,--OnLine为oracle关键字，不可以作为字段名，用OnLinere代替
	IP varchar(50) NULL,
	isAlarm int DEFAULT 1 NULL,
	UrlHost varchar(50) NULL,
 CONSTRAINT PK_USERS PRIMARY KEY 
(
	Users_ID 
)
); 



/****** Object:  Table tzhdata    Script Date: 02/19/2014 15:15:50 ******/






CREATE TABLE tzhdata(
	z1 varchar(10) NULL,
	z2 varchar(10) NULL,
	z3 varchar(10) NULL,
	z4 varchar(10) NULL,
	z5 varchar(10) NULL
); 



/****** Object:  Table TexingValue    Script Date: 02/19/2014 15:15:50 ******/






CREATE TABLE TexingValue(
	MeteStyle_ID int NOT NULL,
	MeterTexing_ID int NOT NULL,
	TexingValue varchar(50) NULL
) ;



/****** Object:  Table TemperatureMonth    Script Date: 02/19/2014 15:15:50 ******/






CREATE TABLE TemperatureMonth(
	Value_id int NOT NULL,
	SelectTime varchar(7) NULL,
	Temperature float NULL,
	insertTime date NULL
) ;



/****** Object:  Table TemperatureHour    Script Date: 02/19/2014 15:15:50 ******/




CREATE TABLE TemperatureHour(
	Value_id int NOT NULL,
	SelectTime date NULL,
	Temperature decimal(9, 2) NULL,
	insertTime date NULL
) ;

/****** Object:  Table TemperatureDay    Script Date: 02/19/2014 15:15:50 ******/






CREATE TABLE TemperatureDay(
	Value_id int NOT NULL,
	SelectTime varchar(10) NULL,
	Temperature float NULL,
	insertTime date NULL
) ;



/****** Object:  Table TargetWaterDate    Script Date: 02/19/2014 15:15:50 ******/






CREATE TABLE TargetWaterDate(
	TargetDate_ID int NOT NULL,
	Target_Index varchar(10) NULL,
	Target_Style int NULL,
	TargetDate_Time varchar(10) NULL,
	TargetGross decimal(18, 2) NULL,
	TargetDate_ZGross decimal(18, 2) NULL,
	TargetDate_Cha decimal(18, 2) NULL,
	TargetDate_Per float NULL,
	TargetDate_Remark varchar(200) NULL,
	inserttime date NULL,
 CONSTRAINT PK_TargetWaterDate PRIMARY KEY 
(
	TargetDate_ID 
)
) ;



/****** Object:  Table TargetWaterAudit    Script Date: 02/19/2014 15:15:50 ******/






CREATE TABLE TargetWaterAudit(
	TargetAudit_ID int NOT NULL,
	Target_ID int NULL,
	TargetAudit_Jan decimal(18, 2) NULL,
	TargetAudit_Feb decimal(18, 2) NULL,
	TargetAudit_Mar decimal(18, 2) NULL,
	TargetAudit_Apr decimal(18, 2) NULL,
	TargetAudit_May decimal(18, 2) NULL,
	TargetAudit_Jun decimal(18, 2) NULL,
	TargetAudit_Jul decimal(18, 2) NULL,
	TargetAudit_Aug decimal(18, 2) NULL,
	TargetAudit_Sept decimal(18, 2) NULL,
	TargetAudit_Oct decimal(18, 2) NULL,
	TargetAudit_Nov decimal(18, 2) NULL,
	TargetAudit_Dec decimal(18, 2) NULL,
	TargetAudit_LastTime date NULL,
	TargetAudit_LastState varchar(20) NULL,
	TargetAudit_LastMan varchar(20) NULL,
 CONSTRAINT PK_TargetWaterAudit PRIMARY KEY 
(
	TargetAudit_ID 
)
) ;



/****** Object:  Table TargetWater    Script Date: 02/19/2014 15:15:50 ******/






CREATE TABLE TargetWater(
	Target_ID int NOT NULL,
	Target_Index varchar(10) NULL,
	Target_Style int NULL,
	Target_fenlei varchar(5) NULL,
	Target_Name varchar(100) NULL,
	Target_Year int NULL,
	Target_Jan decimal(18, 2) NULL,
	Target_Feb decimal(18, 2) NULL,
	Target_Mar decimal(18, 2) NULL,
	Target_Apr decimal(18, 2) NULL,
	Target_May decimal(18, 2) NULL,
	Target_Jun decimal(18, 2) NULL,
	Target_Jul decimal(18, 2) NULL,
	Target_Aug decimal(18, 2) NULL,
	Target_Sept decimal(18, 2) NULL,
	Target_Oct decimal(18, 2) NULL,
	Target_Nov decimal(18, 2) NULL,
	Target_Dec decimal(18, 2) NULL,
	Target_Man int NULL,
	Target_Time date NULL,
 CONSTRAINT PK_TargetWater PRIMARY KEY 
(
	Target_ID 
)
) ;



/******
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'0全部，1区域，2建筑，3建筑类型，4楼层，5表计，6部门' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TargetWater', @level2type=N'COLUMN',@level2name=N'Target_Style'

/****** Object:  Table TargetAmDate    Script Date: 02/19/2014 15:15:50 ******/






CREATE TABLE TargetAmDate(
	TargetDate_ID int NOT NULL,
	Target_Index varchar(10) NULL,
	Target_Style int NULL,
	TargetDate_Time varchar(10) NULL,
	TargetGross decimal(18, 2) NULL,
	TargetDate_ZGross decimal(18, 2) NULL,
	TargetDate_Cha decimal(18, 2) NULL,
	TargetDate_Per float NULL,
	TargetDate_Remark varchar(200) NULL,
	inserttime date NULL,
 CONSTRAINT PK_TargetAmDate PRIMARY KEY 
(
	TargetDate_ID 
)
);



/****** Object:  Table TargetAmAudit    Script Date: 02/19/2014 15:15:50 ******/






CREATE TABLE TargetAmAudit(
	TargetAudit_ID int NOT NULL,
	Target_ID int NULL,
	TargetAudit_Jan decimal(18, 2) NULL,
	TargetAudit_Feb decimal(18, 2) NULL,
	TargetAudit_Mar decimal(18, 2) NULL,
	TargetAudit_Apr decimal(18, 2) NULL,
	TargetAudit_May decimal(18, 2) NULL,
	TargetAudit_Jun decimal(18, 2) NULL,
	TargetAudit_Jul decimal(18, 2) NULL,
	TargetAudit_Aug decimal(18, 2) NULL,
	TargetAudit_Sept decimal(18, 2) NULL,
	TargetAudit_Oct decimal(18, 2) NULL,
	TargetAudit_Nov decimal(18, 2) NULL,
	TargetAudit_Dec decimal(18, 2) NULL,
	TargetAudit_LastTime date NULL,
	TargetAudit_LastState varchar(20) NULL,
	TargetAudit_LastMan varchar(20) NULL,
 CONSTRAINT PK_TargetAmAudit PRIMARY KEY 
(
	TargetAudit_ID 
)
) ;



/****** Object:  Table TargetAm    Script Date: 02/19/2014 15:15:50 ******/






CREATE TABLE TargetAm(
	Target_ID int NOT NULL,
	Target_Index varchar(10) NULL,
	Target_Style int NULL,
	Target_fenlei varchar(5) NULL,
	Target_Name varchar(100) NULL,
	Target_Year int NULL,
	Target_Jan decimal(18, 2) NULL,
	Target_Feb decimal(18, 2) NULL,
	Target_Mar decimal(18, 2) NULL,
	Target_Apr decimal(18, 2) NULL,
	Target_May decimal(18, 2) NULL,
	Target_Jun decimal(18, 2) NULL,
	Target_Jul decimal(18, 2) NULL,
	Target_Aug decimal(18, 2) NULL,
	Target_Sept decimal(18, 2) NULL,
	Target_Oct decimal(18, 2) NULL,
	Target_Nov decimal(18, 2) NULL,
	Target_Dec decimal(18, 2) NULL,
	Target_Man int NULL,
	Target_Time date NULL,
 CONSTRAINT PK_TargetAm PRIMARY KEY 
(
	Target_ID 
)
); 



/******
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'0全部，1区域，2建筑，3建筑类型，4楼层，5表计，6部门' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TargetAm', @level2type=N'COLUMN',@level2name=N'Target_Style'

/****** Object:  Table T_PartmentStyleDayWater    Script Date: 02/19/2014 15:15:50 ******/




CREATE TABLE T_PartmentStyleDayWater(
	Partment_ID int NOT NULL,
	SelectYear int NOT NULL,
	SelectMonth int NOT NULL,
	SelectDay int NOT NULL,
	UseAmStyle int NOT NULL,
	ZGross decimal(18, 2) NULL,
	ZMoney decimal(18, 5) NULL,
	WorkData decimal(18, 2) NULL,
	WorkMoney decimal(18, 2) NULL,
	UnWorkData decimal(18, 2) NULL,
	UnWorkMoney decimal(18, 5) NULL,
 CONSTRAINT PK_T_PartmentStyleDayWater PRIMARY KEY 
(
	Partment_ID ,
	SelectYear ,
	SelectMonth ,
	SelectDay ,
	UseAmStyle 
)
); 

/****** Object:  Table T_PartmentStyleDayGas    Script Date: 02/19/2014 15:15:50 ******/




CREATE TABLE T_PartmentStyleDayGas(
	Partment_ID int NOT NULL,
	SelectYear int NOT NULL,
	SelectMonth int NOT NULL,
	SelectDay int NOT NULL,
	UseAmStyle int NOT NULL,
	ZGross decimal(18, 2) NULL,
	ZMoney decimal(18, 5) NULL,
	WorkData decimal(18, 2) NULL,
	WorkMoney decimal(18, 2) NULL,
	UnWorkData decimal(18, 2) NULL,
	UnWorkMoney decimal(18, 5) NULL,
 CONSTRAINT PK_T_PartmentStyleDayGas PRIMARY KEY 
(
	Partment_ID ,
	SelectYear ,
	SelectMonth ,
	SelectDay ,
	UseAmStyle 
)
) ;

/****** Object:  Table T_PartmentStyleDayAm    Script Date: 02/19/2014 15:15:50 ******/




CREATE TABLE T_PartmentStyleDayAm(
	Partment_ID int NOT NULL,
	SelectYear int NOT NULL,
	SelectMonth int NOT NULL,
	SelectDay int NOT NULL,
	UseAmStyle int NOT NULL,
	ZGross decimal(18, 2) NULL,
	ZMoney decimal(18, 5) NULL,
	WorkData decimal(18, 2) NULL,
	WorkMoney decimal(18, 2) NULL,
	UnWorkData decimal(18, 2) NULL,
	UnWorkMoney decimal(18, 5) NULL,
 CONSTRAINT PK_T_PartmentStyleDayAm PRIMARY KEY 
(
	Partment_ID ,
	SelectYear ,
	SelectMonth ,
	SelectDay ,
	UseAmStyle 
)
); 

/****** Object:  Table T_PartmentFenleiDayWater    Script Date: 02/19/2014 15:15:50 ******/






CREATE TABLE T_PartmentFenleiDayWater(
	Partment_ID int NOT NULL,
	SelectYear int NOT NULL,
	SelectMonth int NOT NULL,
	SelectDay int NOT NULL,
	Fenlei varchar(2) NOT NULL,
	Fenlei01 varchar(2) NOT NULL,
	Fenlei02 varchar(2) NOT NULL,
	ZGross decimal(18, 2) NULL,
	ZMoney decimal(18, 5) NULL,
	WorkData decimal(18, 2) NULL,
	WorkMoney decimal(18, 2) NULL,
	UnWorkData decimal(18, 2) NULL,
	UnWorkMoney decimal(18, 5) NULL,
 CONSTRAINT PK_T_PartmentFenleiDayWater PRIMARY KEY 
(
	Partment_ID ,
	SelectYear ,
	SelectMonth ,
	SelectDay ,
	Fenlei ,
	Fenlei01 ,
	Fenlei02 
)
); 



/****** Object:  Table T_PartmentFenleiDayGas    Script Date: 02/19/2014 15:15:50 ******/






CREATE TABLE T_PartmentFenleiDayGas(
	Partment_ID int NOT NULL,
	SelectYear int NOT NULL,
	SelectMonth int NOT NULL,
	SelectDay int NOT NULL,
	Fenlei varchar(2) NOT NULL,
	Fenlei01 varchar(2) NOT NULL,
	Fenlei02 varchar(2) NOT NULL,
	ZGross decimal(18, 2) NULL,
	ZMoney decimal(18, 5) NULL,
	WorkData decimal(18, 2) NULL,
	WorkMoney decimal(18, 2) NULL,
	UnWorkData decimal(18, 2) NULL,
	UnWorkMoney decimal(18, 5) NULL,
 CONSTRAINT PK_T_PartmentFenleiDayGas PRIMARY KEY 
(
	Partment_ID ,
	SelectYear ,
	SelectMonth ,
	SelectDay ,
	Fenlei ,
	Fenlei01 ,
	Fenlei02 
)
); 



/****** Object:  Table T_PartmentFenleiDayAm    Script Date: 02/19/2014 15:15:50 ******/






CREATE TABLE T_PartmentFenleiDayAm(
	Partment_ID int NOT NULL,
	SelectYear int NOT NULL,
	SelectMonth int NOT NULL,
	SelectDay int NOT NULL,
	Fenlei varchar(2) NOT NULL,
	Fenlei01 varchar(2) NOT NULL,
	Fenlei02 varchar(2) NOT NULL,
	ZGross decimal(18, 2) NULL,
	ZMoney decimal(18, 5) NULL,
	WorkData decimal(18, 2) NULL,
	WorkMoney decimal(18, 2) NULL,
	UnWorkData decimal(18, 2) NULL,
	UnWorkMoney decimal(18, 5) NULL,
 CONSTRAINT PK_T_PartmentFenleiDayAm PRIMARY KEY 
(
	Partment_ID ,
	SelectYear ,
	SelectMonth ,
	SelectDay ,
	Fenlei ,
	Fenlei01 ,
	Fenlei02 
)
); 



/****** Object:  Table T_PartmentDayWater    Script Date: 02/19/2014 15:15:50 ******/




CREATE TABLE T_PartmentDayWater(
	Partment_ID int NOT NULL,
	SelectYear int NOT NULL,
	SelectMonth int NOT NULL,
	SelectDay int NOT NULL,
	ZGross decimal(18, 2) NULL,
	ZMoney decimal(18, 5) NULL,
	WorkData decimal(18, 2) NULL,
	WorkMoney decimal(18, 2) NULL,
	UnWorkData decimal(18, 2) NULL,
	UnWorkMoney decimal(18, 5) NULL,
 CONSTRAINT PK_T_PartmentDayWater PRIMARY KEY 
(
	Partment_ID ,
	SelectYear ,
	SelectMonth ,
	SelectDay 
)
); 

/****** Object:  Table T_PartmentDayGas    Script Date: 02/19/2014 15:15:50 ******/




CREATE TABLE T_PartmentDayGas(
	Partment_ID int NOT NULL,
	SelectYear int NOT NULL,
	SelectMonth int NOT NULL,
	SelectDay int NOT NULL,
	ZGross decimal(18, 2) NULL,
	ZMoney decimal(18, 5) NULL,
	WorkData decimal(18, 2) NULL,
	WorkMoney decimal(18, 2) NULL,
	UnWorkData decimal(18, 2) NULL,
	UnWorkMoney decimal(18, 5) NULL,
 CONSTRAINT PK_T_PartmentDayGas PRIMARY KEY 
(
	Partment_ID ,
	SelectYear ,
	SelectMonth ,
	SelectDay 
)
) ;

/****** Object:  Table T_PartmentDayAm    Script Date: 02/19/2014 15:15:50 ******/




CREATE TABLE T_PartmentDayAm(
	Partment_ID int NOT NULL,
	SelectYear int NOT NULL,
	SelectMonth int NOT NULL,
	SelectDay int NOT NULL,
	ZGross decimal(18, 2) NULL,
	ZMoney decimal(18, 5) NULL,
	WorkData decimal(18, 2) NULL,
	WorkMoney decimal(18, 2) NULL,
	UnWorkData decimal(18, 2) NULL,
	UnWorkMoney decimal(18, 5) NULL,
 CONSTRAINT PK_T_PartmentDayAm PRIMARY KEY 
(
	Partment_ID ,
	SelectYear ,
	SelectMonth ,
	SelectDay 
)
); 

/****** Object:  Table T_MonthWater    Script Date: 02/19/2014 15:15:50 ******/






CREATE TABLE T_MonthWater(
	WaterMeter_ID int NOT NULL,
	SelectYear int NOT NULL,
	SelectMonth int NOT NULL,
	WaterMeter_Num varchar(20) NULL,
	Area_ID int NULL,
	Architecture_ID int NULL,
	UseAmStyle int NULL,
	Partment int NULL,
	ZGross decimal(18, 2) NULL,
	ZMoney decimal(18, 3) NULL,
	IsCountMeter int NULL,
	isComputation int NULL,
 CONSTRAINT PK_T_MonthWater PRIMARY KEY 
(
	WaterMeter_ID ,
	SelectYear ,
	SelectMonth 
)
) ;



/****** Object:  Table T_MonthGas    Script Date: 02/19/2014 15:15:50 ******/






CREATE TABLE T_MonthGas(
	GasMeter_ID int NOT NULL,
	SelectYear int NOT NULL,
	SelectMonth int NOT NULL,
	GasMeter_Num varchar(20) NULL,
	Area_ID int NULL,
	Architecture_ID int NULL,
	UseAmStyle int NULL,
	Partment int NULL,
	ZGross decimal(18, 2) NULL,
	ZMoney decimal(18, 3) NULL,
	IsCountMeter int NULL,
	isComputation int NULL,
 CONSTRAINT PK_T_MonthGas PRIMARY KEY 
(
	GasMeter_ID ,
	SelectYear ,
	SelectMonth 
)
) ;



/****** Object:  Table T_MonthAm    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE T_MonthAm(
	AmMeter_ID int NOT NULL,
	SelectYear int NOT NULL,
	SelectMonth int NOT NULL,
	AmMeter_Num varchar(20) NULL,
	Area_ID int NULL,
	Architecture_ID int NULL,
	UseAmStyle int NULL,
	Partment int NULL,
	ZGross decimal(18, 2) NULL,
	ZMoney decimal(18, 3) NULL,
	IsCountMeter int NULL,
	isComputation int NULL,
 CONSTRAINT PK_T_MonthAm PRIMARY KEY 
(
	AmMeter_ID ,
	SelectYear ,
	SelectMonth 
)
) ;



/****** Object:  Table T_DayWater    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE T_DayWater(
	WaterMeter_ID int NOT NULL,
	SelectYear int NOT NULL,
	SelectMonth int NOT NULL,
	SelectDay int NOT NULL,
	WaterMeter_Num varchar(20) NULL,
	Area_ID int NULL,
	Architecture_ID int NULL,
	Partment int NULL,
	StarReadingDate date NULL,
	StarZValueZY decimal(18, 2) NULL,
	EndReadingDate date NULL,
	EndZValueZY decimal(18, 2) NULL,
	ZGross decimal(18, 2) NULL,
	WorkData decimal(18, 2) NULL,
	UnWorkData decimal(18, 2) NULL,
	IsCountMeter int NULL,
	isComputation int NULL,
	Price_Value decimal(18, 3) NULL,
 CONSTRAINT PK_T_DayWater PRIMARY KEY 
(
	WaterMeter_ID ,
	SelectYear ,
	SelectMonth ,
	SelectDay 
)
) ;



/****** Object:  Table T_DayGas    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE T_DayGas(
	GasMeter_ID int NOT NULL,
	SelectYear int NOT NULL,
	SelectMonth int NOT NULL,
	SelectDay int NOT NULL,
	GasMeter_Num varchar(20) NULL,
	Area_ID int NULL,
	Architecture_ID int NULL,
	Partment int NULL,
	StarReadingDate date NULL,
	StarZValueZY decimal(18, 2) NULL,
	EndReadingDate date NULL,
	EndZValueZY decimal(18, 2) NULL,
	ZGross decimal(18, 2) NULL,
	WorkData decimal(18, 2) NULL,
	UnWorkData decimal(18, 2) NULL,
	IsCountMeter int NULL,
	isComputation int NULL,
	Price_Value decimal(18, 3) NULL,
 CONSTRAINT PK_T_DayGas PRIMARY KEY 
(
	GasMeter_ID ,
	SelectYear ,
	SelectMonth ,
	SelectDay 
)
) ;



/****** Object:  Table T_DayAm    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE T_DayAm(
	AmMeter_ID int NOT NULL,
	SelectYear int NOT NULL,
	SelectMonth int NOT NULL,
	SelectDay int NOT NULL,
	AmMeter_Num varchar(20) NULL,
	Area_ID int NULL,
	Architecture_ID int NULL,
	Partment int NULL,
	StarReadingDate date NULL,
	StarZValueZY decimal(18, 2) NULL,
	EndReadingDate date NULL,
	EndZValueZY decimal(18, 2) NULL,
	ZGross decimal(18, 2) NULL,
	WorkData decimal(18, 2) NULL,
	UnWorkData decimal(18, 2) NULL,
	IsCountMeter int NULL,
	isComputation int NULL,
	Price_Value decimal(18, 3) NULL,
 CONSTRAINT PK_T_DayAm PRIMARY KEY 
(
	AmMeter_ID ,
	SelectYear ,
	SelectMonth ,
	SelectDay 
)
) ;



/****** Object:  Table T_ArcStyleDayWater    Script Date: 02/19/2014 15:15:49 ******/




CREATE TABLE T_ArcStyleDayWater(
	Architecture_ID int NOT NULL,
	Area_ID int NULL,
	SelectYear int NOT NULL,
	SelectMonth int NOT NULL,
	SelectDay int NOT NULL,
	UseAmStyle int NOT NULL,
	ZGross decimal(18, 2) NULL,
	ZMoney decimal(18, 5) NULL,
	WorkData decimal(18, 2) NULL,
	WorkMoney decimal(18, 2) NULL,
	UnWorkData decimal(18, 2) NULL,
	UnWorkMoney decimal(18, 5) NULL,
 CONSTRAINT PK_T_ArcStyleDayWater PRIMARY KEY 
(
	Architecture_ID ,
	SelectYear ,
	SelectMonth ,
	SelectDay ,
	UseAmStyle 
)
) ;

/****** Object:  Table T_ArcStyleDayGas    Script Date: 02/19/2014 15:15:49 ******/




CREATE TABLE T_ArcStyleDayGas(
	Architecture_ID int NOT NULL,
	Area_ID int NULL,
	SelectYear int NOT NULL,
	SelectMonth int NOT NULL,
	SelectDay int NOT NULL,
	UseAmStyle int NOT NULL,
	ZGross decimal(18, 2) NULL,
	ZMoney decimal(18, 5) NULL,
	WorkData decimal(18, 2) NULL,
	WorkMoney decimal(18, 2) NULL,
	UnWorkData decimal(18, 2) NULL,
	UnWorkMoney decimal(18, 5) NULL,
 CONSTRAINT PK_T_ArcStyleDayGas PRIMARY KEY 
(
	Architecture_ID ,
	SelectYear ,
	SelectMonth ,
	SelectDay ,
	UseAmStyle 
)
); 

/****** Object:  Table T_ArcStyleDayAm    Script Date: 02/19/2014 15:15:49 ******/




CREATE TABLE T_ArcStyleDayAm(
	Architecture_ID int NOT NULL,
	Area_ID int NULL,
	SelectYear int NOT NULL,
	SelectMonth int NOT NULL,
	SelectDay int NOT NULL,
	UseAmStyle int NOT NULL,
	ZGross decimal(18, 2) NULL,
	ZMoney decimal(18, 5) NULL,
	WorkData decimal(18, 2) NULL,
	WorkMoney decimal(18, 2) NULL,
	UnWorkData decimal(18, 2) NULL,
	UnWorkMoney decimal(18, 5) NULL,
 CONSTRAINT PK_T_ArcStyleDayAm PRIMARY KEY 
(
	Architecture_ID ,
	SelectYear ,
	SelectMonth ,
	SelectDay ,
	UseAmStyle 
)
) ;

/****** Object:  Table T_ArcFenleiDayWater    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE T_ArcFenleiDayWater(
	Architecture_ID int NOT NULL,
	Area_ID int NULL,
	SelectYear int NOT NULL,
	SelectMonth int NOT NULL,
	SelectDay int NOT NULL,
	Fenlei varchar(2) NOT NULL,
	Fenlei01 varchar(2) NOT NULL,
	Fenlei02 varchar(2) NOT NULL,
	ZGross decimal(18, 2) NULL,
	ZMoney decimal(18, 5) NULL,
	WorkData decimal(18, 2) NULL,
	WorkMoney decimal(18, 2) NULL,
	UnWorkData decimal(18, 2) NULL,
	UnWorkMoney decimal(18, 5) NULL,
 CONSTRAINT PK_T_ArcFenleiDayWater PRIMARY KEY 
(
	Architecture_ID ,
	SelectYear ,
	SelectMonth ,
	SelectDay ,
	Fenlei ,
	Fenlei01 ,
	Fenlei02 
)
) ;



/****** Object:  Table T_ArcFenleiDayGas    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE T_ArcFenleiDayGas(
	Architecture_ID int NOT NULL,
	Area_ID int NULL,
	SelectYear int NOT NULL,
	SelectMonth int NOT NULL,
	SelectDay int NOT NULL,
	Fenlei varchar(2) NOT NULL,
	Fenlei01 varchar(2) NOT NULL,
	Fenlei02 varchar(2) NOT NULL,
	ZGross decimal(18, 2) NULL,
	ZMoney decimal(18, 5) NULL,
	WorkData decimal(18, 2) NULL,
	WorkMoney decimal(18, 2) NULL,
	UnWorkData decimal(18, 2) NULL,
	UnWorkMoney decimal(18, 5) NULL,
 CONSTRAINT PK_T_ArcFenleiDayGas PRIMARY KEY 
(
	Architecture_ID ,
	SelectYear ,
	SelectMonth ,
	SelectDay ,
	Fenlei ,
	Fenlei01 ,
	Fenlei02 
)
) ;



/****** Object:  Table T_ArcFenleiDayAm    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE T_ArcFenleiDayAm(
	Architecture_ID int NOT NULL,
	Area_ID int NULL,
	SelectYear int NOT NULL,
	SelectMonth int NOT NULL,
	SelectDay int NOT NULL,
	Fenlei varchar(2) NOT NULL,
	Fenlei01 varchar(2) NOT NULL,
	Fenlei02 varchar(2) NOT NULL,
	ZGross decimal(18, 2) NULL,
	ZMoney decimal(18, 5) NULL,
	WorkData decimal(18, 2) NULL,
	WorkMoney decimal(18, 2) NULL,
	UnWorkData decimal(18, 2) NULL,
	UnWorkMoney decimal(18, 5) NULL,
 CONSTRAINT PK_T_ArcFenleiDayAm PRIMARY KEY 
(
	Architecture_ID ,
	SelectYear ,
	SelectMonth ,
	SelectDay ,
	Fenlei ,
	Fenlei01 ,
	Fenlei02 
)
) ;



/****** Object:  Table T_ArcDayWater    Script Date: 02/19/2014 15:15:49 ******/




CREATE TABLE T_ArcDayWater(
	Architecture_ID int NOT NULL,
	Area_ID int NULL,
	SelectYear int NOT NULL,
	SelectMonth int NOT NULL,
	SelectDay int NOT NULL,
	ZGross decimal(18, 2) NULL,
	ZMoney decimal(18, 5) NULL,
	WorkData decimal(18, 2) NULL,
	WorkMoney decimal(18, 2) NULL,
	UnWorkData decimal(18, 2) NULL,
	UnWorkMoney decimal(18, 5) NULL,
 CONSTRAINT PK_T_ArcDayWater PRIMARY KEY 
(
	Architecture_ID ,
	SelectYear ,
	SelectMonth ,
	SelectDay 
)
); 

/****** Object:  Table T_ArcDayGas    Script Date: 02/19/2014 15:15:49 ******/




CREATE TABLE T_ArcDayGas(
	Architecture_ID int NOT NULL,
	Area_ID int NULL,
	SelectYear int NOT NULL,
	SelectMonth int NOT NULL,
	SelectDay int NOT NULL,
	ZGross decimal(18, 2) NULL,
	ZMoney decimal(18, 5) NULL,
	WorkData decimal(18, 2) NULL,
	WorkMoney decimal(18, 2) NULL,
	UnWorkData decimal(18, 2) NULL,
	UnWorkMoney decimal(18, 5) NULL,
 CONSTRAINT PK_T_ArcDayGas PRIMARY KEY 
(
	Architecture_ID ,
	SelectYear ,
	SelectMonth ,
	SelectDay 
)
) ;

/****** Object:  Table T_ArcDayAm    Script Date: 02/19/2014 15:15:49 ******/




CREATE TABLE T_ArcDayAm(
	Architecture_ID int NOT NULL,
	Area_ID int NULL,
	SelectYear int NOT NULL,
	SelectMonth int NOT NULL,
	SelectDay int NOT NULL,
	ZGross decimal(18, 2) NULL,
	ZMoney decimal(18, 5) NULL,
	WorkData decimal(18, 2) NULL,
	WorkMoney decimal(18, 2) NULL,
	UnWorkData decimal(18, 2) NULL,
	UnWorkMoney decimal(18, 5) NULL,
 CONSTRAINT PK_T_ArcDayAm PRIMARY KEY 
(
	Architecture_ID ,
	SelectYear ,
	SelectMonth ,
	SelectDay 
)
); 

/****** Object:  Table SystemLog    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE SystemLog(
	SystemLog_ID int NOT NULL,
	Users_ID numeric(18, 0) NULL,
	Module_Num varchar(4) NULL,
	SystemLog_Action varchar(50) NULL,
	SystemLog_Result int NULL,
	SystemLog_Time date NULL,
	SystemLog_Message varchar(200) NULL
); 



/****** Object:  Table SystemInfo    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE SystemInfo(
	SystemInfo_ID int NOT NULL,
	SystemInfo_ComplayName varchar(250) NULL,
	SystemInfo_ComplayNum varchar(50) NULL,
	SystemInfo_ComplayShot varchar(50) NULL,
	SystemInfo_Lo int NULL,
	SystemInfo_StarYear int NULL,
	SystemInfo_Danwei varchar(50) NULL,
	SystemInfo_XZNum varchar(20) NULL,
	WorkingStartTime varchar(8) NULL,
	WorkingEndTime varchar(8) NULL,
	ManualEnergyNum varchar(50) NULL,
	ComplayIntroduction varchar(2000) NULL,
	ComplayImg varchar(200) NULL,
	EnReportServer varchar(200) NULL,
	EnReportPort int NULL,
	SystemInfo_StarMonth int NULL,
	SystemInfo_StarDay int NULL,
 CONSTRAINT PK_SystemInfo PRIMARY KEY 
(
	SystemInfo_ID 
)
); 



/****** Object:  Table SMSWill    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE SMSWill(
	SMSWill_ID int NOT NULL,
	SMSWill_PhoneList varchar(500) NULL,
	SMSWill_Content varchar(1000) NULL,
	SMSWill_State int NULL,
	SMSWill_RepeatTime int NULL,
	Users_ID int NULL,
	inserttime date NULL,
	Module_Num varchar(10) NULL,
 CONSTRAINT PK_SMSWill PRIMARY KEY 
(
	SMSWill_ID 
)
) ;



/****** Object:  Table SMSReceive    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE SMSReceive(
	SMSReceive_ID int NOT NULL,
	SendPhone varchar(20) NULL,
	SendTime date NULL,
	ConText varchar(1000) NULL,
	ReceiveMan varchar(50) NULL,
	Module_Num varchar(10) NULL,
 CONSTRAINT PK_SMSReceive PRIMARY KEY 
(
	SMSReceive_ID 
)
) ;



/****** Object:  Table SMSManage    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE SMSManage(
	SMSManage_ID int NOT NULL,
	SMSManage_Num varchar(50) NULL,
	SMSManage_Name varchar(100) NULL,
	SMSManage_State int NULL,
	SMSManage_Short varchar(50) NULL,
	SMSManage_Price float NULL,
	SMSManage_Remain float NULL,
	SMSManage_Remark varchar(200) NULL,
	SMSManage_Part varchar(10) NULL,
	SMSManage_Password varchar(20) NULL,
 CONSTRAINT PK_SMSManage PRIMARY KEY 
(
	SMSManage_ID 
)
) ;



/****** Object:  Table SMSHistory    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE SMSHistory(
	SMSHistory_ID int NOT NULL,
	SMSHistory_Time date NULL,
	SMSHistory_PhoneList varchar(1000) NULL,
	SMSHistory_Count int NULL,
	SMSHistory_Content varchar(500) NULL,
	Users_ID int NULL,
	SMSHistory_Style int NULL,
	SMSManage_ID int NULL,
	SMSHistory_Timing int NULL,
	inserttime date NULL
); 



/****** Object:  Table SLSchedule    Script Date: 02/19/2014 15:15:49 ******/




CREATE TABLE SLSchedule(
	SwitchScheduleID numeric(18, 0) NOT NULL,
	Type int NOT NULL,
	Area_ID int NULL,
	SLLine_ID int NULL,
	SLLamp_ID int NULL,
	yyyy int NOT NULL,
	mm int NOT NULL,
	dd int NOT NULL,
	Onhh1 int NOT NULL,
	Onmm1 int NOT NULL,
	Offhh1 int NOT NULL,
	Offmm1 int NOT NULL,
	Onhh2 int NULL,
	Onmm2 int NULL,
	Offhh2 int NULL,
	Offmm2 int NULL
) ;

/****** Object:  Table SLPlanStep    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE SLPlanStep(
	SLPlan_ID int NULL,
	SLPlanStep_Name varchar(20) NULL,
	SLPlanStep_Time varchar(10) NULL
) ;



/****** Object:  Table SLPlanState    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE SLPlanState(
	SLPlan_ID int NULL,
	SLPlanStep_Name varchar(10) NULL,
	SLIndex int NULL,
	SLKZStyle int NULL,
	Enable int NULL,
	SLLampState int NULL,
	intPerCent int NULL
) ;



/****** Object:  Table SLPlan    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE SLPlan(
	SLPlan_ID int NOT NULL,
	SLPlan_Name varchar(50) NULL,
	SLPlan_Enable varchar(5) NULL,
	SLPlan_State varchar(10) NULL,
	SLPlan_IsExec varchar(5) NULL,
	SLPlan_LastState varchar(5) NULL,
	SLPlan_LastTime date NULL,
	SLPlan_NextTime date NULL,
	SLPlan_LastEditTime date NULL,
	SLPlan_InsertTime date NULL,
	SLPlan_Remark varchar(200) NULL,
 CONSTRAINT PK_SLPlan PRIMARY KEY 
(
	SLPlan_ID 
)
) ;



/****** Object:  Table SLLine    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE SLLine(
	SLLine_ID int NOT NULL,
	Area_ID int NULL,
	Gather_ID int NULL,
	SLKongZhi_ID int NULL,
	SLKongzhi_Index int NULL,
	AmMeter_ID int NULL,
	SLLine_Num varchar(20) NULL,
	SLLine_Name varchar(50) NULL,
	SLLine_Star varchar(50) NULL,
	SLLine_End varchar(50) NULL,
 CONSTRAINT PK_SLLine PRIMARY KEY 
(
	SLLine_ID 
)
) ;



/****** Object:  Table SLLamp    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE SLLamp(
	SLLamp_ID int NOT NULL,
	Area_ID int NULL,
	Gather_ID int NULL,
	SLKongzhi_ID int NULL,
	SLLine_ID int NULL,
	SLLamp_Num varchar(20) NULL,
	SLLamp_State varchar(5) NULL,
	SLLamp_Size varchar(20) NULL,
	LastTime date NULL,
	intPerCent int NULL,
 CONSTRAINT PK_SLLamp PRIMARY KEY 
(
	SLLamp_ID 
)
) ;



/****** Object:  Table SLKongzhi    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE SLKongzhi(
	SLKongzhi_ID int NOT NULL,
	Area_ID int NULL,
	SLGather_ID int NULL,
	SLKongzhi_Num varchar(20) NULL,
	SLKongzhi_Name varchar(20) NULL,
	SLKongzhi_485Address varchar(10) NULL,
	SLKongzhi_Size varchar(10) NULL,
	SLKongzhi_Port int NULL,
	Version varchar(50) NULL,
	Lamp_State1 varchar(5) NULL,
	Lamp_State2 varchar(5) NULL,
	Lamp_AccTime1 int NULL,
	Lamp_AccTime2 int NULL,
	LastTime date NULL,
	SLKongzhi_State int NULL,
	Lamp_State11 varchar(10) NULL,
	Lamp_State21 varchar(10) NULL,
 CONSTRAINT PK_SLKongzhiqi PRIMARY KEY 
(
	SLKongzhi_ID 
)
) ;



/****** Object:  Table SLHistory    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE SLHistory(
	SLHistory_ID int NOT NULL,
	SLHistory_Time date NULL,
	SLHistory_State varchar(5) NULL,
	Users_ID int NULL,
	SLHistory_Style varchar(5) NULL,
	SLHistory_Opr varchar(200) NULL,
 CONSTRAINT PK_SLHistory PRIMARY KEY 
(
	SLHistory_ID 
)
); 



/****** Object:  Table SLDiaoDu    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE SLDiaoDu(
	SLDiaoDu_ID int NOT NULL,
	SLPlan_ID int NULL,
	SLDiaoDu_Type int NULL,
	SLDiaoDu_YiCiTime varchar(10) NULL,
	SLDiaoDu_TimeList varchar(100) NULL,
	SLDiaoDu_PVType varchar(4) NULL,
	SLDiaoDu_PVJiange int NULL,
	SLDiaoDu_PVRmark1 varchar(50) NULL,
	SLDiaoDu_PVRmark2 varchar(20) NULL,
	SLDiaoDu_StarTime varchar(10) NULL,
	SLDiaoDu_EndType int NULL,
	SLDiaoDu_EndTime varchar(10) NULL,
 CONSTRAINT PK_SLDiaoDu PRIMARY KEY 
(
	SLDiaoDu_ID 
)
); 



/******
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'当间隔类型为月，间隔为0时指的是按周计算' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'SLDiaoDu', @level2type=N'COLUMN',@level2name=N'SLDiaoDu_PVJiange'

/****** Object:  Table ServerInfo    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE ServerInfo(
	ServerInfo_ID int NOT NULL,
	ServerInfo_IP varchar(50) NULL,
	ServerInfo_Prot int NULL,
	ServerInfo_MAXNUM int NULL,
	ServerInfo_WebIP varchar(50) NULL,
	ServerInfo_WebProt int NULL,
	ServerInfo_WebName varchar(100) NULL,
	ServerInfo_OverTime int NULL,
	ServerInfo_ExternIP varchar(50) NULL,
	ServerInfo_ExternWebIP varchar(50) NULL,
 CONSTRAINT PK_ServerInfo PRIMARY KEY 
(
	ServerInfo_ID 
)
) ;



/****** Object:  Table Roles_Power    Script Date: 02/19/2014 15:15:49 ******/




CREATE TABLE Roles_Power(
	Roles_ID numeric(18, 0) NOT NULL,
	Power_ID numeric(18, 0) NOT NULL,
 CONSTRAINT PK_Roles_Power PRIMARY KEY 
(
	Roles_ID ,
	Power_ID 
)
) ;

/****** Object:  Table Roles    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE Roles(
	Roles_ID numeric(18, 0) NOT NULL,
	Roles_Name varchar(50) NOT NULL,
	Roles_Enable int NOT NULL,
	Roles_Remark varchar(250) NULL,
 CONSTRAINT PK_ROLES PRIMARY KEY 
(
	Roles_ID 
)
) ;



/****** Object:  Table PublicityTemplates    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE PublicityTemplates(
	PublicityTemplates_ID int NOT NULL,
	PublicityTemplates_Name varchar(100) NULL,
	PublicityTemplates_Style varchar(50) NULL,
	PublicityTemplates_Address varchar(200) NULL,
	PublicityTemplates_Remark varchar(1000) NULL,
 CONSTRAINT PK_PublicityTemplates PRIMARY KEY 
(
	PublicityTemplates_ID 
)
) ;



/****** Object:  Table PublicityPD    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE PublicityPD(
	PublicityPD_ID int NOT NULL,
	PublicityPD_Name varchar(50) NULL,
	PublicityPD_Style varchar(20) NULL,
	PublicityPD_HtmlAddress varchar(200) NULL,
	PublicityPD_Part varchar(100) NULL,
	PublicityPD_Enable int NULL,
	PublicityPD_Order int NULL,
	PublicityPD_Remark varchar(200) NULL
) ;

/****** Object:  Table PublicityCon    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE PublicityCon(
	PublicityCon_ID int NOT NULL,
	PublicityPD_ID int NULL,
	PublicityCon_Time date NULL,
	PublicityCon_Man varchar(100) NULL,
	PublicityCon_Title varchar(200) NULL,
	PublicityCon_TitleColor varchar(10) NULL,
	PublicityCon_Thumbnail varchar(500) NULL,
	PublicityCon_Introduction varchar(1000) NULL,
	PublicityCon_Annex varchar(500) NULL,
	PublicityCon_Text varchar(500) NULL,
	PublicityCon_Url varchar(500) NULL,
	IsFocus int NULL,
	IsTop int NULL,
 CONSTRAINT PK_PublicituC primary KEY 
(
	PublicityCon_ID 
)
);  



/******
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'所属频道' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PublicityCon', @level2type=N'COLUMN',@level2name=N'PublicityPD_ID'

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'发布时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PublicityCon', @level2type=N'COLUMN',@level2name=N'PublicityCon_Time'

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'作者' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PublicityCon', @level2type=N'COLUMN',@level2name=N'PublicityCon_Man'

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'标题' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PublicityCon', @level2type=N'COLUMN',@level2name=N'PublicityCon_Title'

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'标题颜色' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PublicityCon', @level2type=N'COLUMN',@level2name=N'PublicityCon_TitleColor'

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'缩略图地址' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PublicityCon', @level2type=N'COLUMN',@level2name=N'PublicityCon_Thumbnail'

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'简介' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PublicityCon', @level2type=N'COLUMN',@level2name=N'PublicityCon_Introduction'

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'附件地址' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PublicityCon', @level2type=N'COLUMN',@level2name=N'PublicityCon_Annex'

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'内容' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PublicityCon', @level2type=N'COLUMN',@level2name=N'PublicityCon_Text'

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'页面地址' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PublicityCon', @level2type=N'COLUMN',@level2name=N'PublicityCon_Url'

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'焦点' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PublicityCon', @level2type=N'COLUMN',@level2name=N'IsFocus'

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'置顶' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PublicityCon', @level2type=N'COLUMN',@level2name=N'IsTop'

/****** Object:  Table Protocol    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE Protocol(
	Protocol_ID numeric(18, 0) NOT NULL,
	Protocol_Num varchar(20) NULL,
	Protocol_Name varchar(50) NULL,
	Protocol_Part1 varchar(50) NULL,
	Protocol_Part2 varchar(50) NULL,
	Protocol_Remark varchar(250) NULL,
 CONSTRAINT PK_Protocol PRIMARY KEY 
(
	Protocol_ID 
)
); 



/****** Object:  Table Price    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE Price(
	Price_ID int NOT NULL,
	Price_Style varchar(5) NULL,
	Price_Num varchar(10) NULL,
	Price_Name varchar(50) NULL,
	Price_Value decimal(8, 3) NULL,
 CONSTRAINT PK_Price PRIMARY KEY 
(
	Price_ID 
)
) ;



/****** Object:  Table Power    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE Power(
	Power_ID numeric(18, 0) NOT NULL,
	Module_ID numeric(18, 0) NULL,
	Power_Num varchar(50) NULL,
	Power_Name varchar(50) NOT NULL,
	Power_Remark varchar(250) NULL,
	Power_Level int NOT NULL
) ;



/****** Object:  Table PlanTask    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE PlanTask(
	PlanTask_ID int NOT NULL,
	PlanTask_Style varchar(20) NULL,
	PlanTask_Remark varchar(200) NULL,
	PlanTask_Exec varchar(20) NULL,
	PlanTask_Tiaojian varchar(200) NULL,
	PlanTask_LastState varchar(50) NULL,
	PlanTask_LastTime date NULL,
	PlanTask_NextTime date NULL,
	PlanTask_01Time varchar(20) NULL,
	PlanTask_02Pinlv varchar(1) NULL,
	PlanTask_02PinlvD int NULL,
	PlanTask_02PinlvMday int NULL,
	PlanTask_02PinlvMmonth int NULL,
	PlanTask_02PinlvStyle int NULL,
	PlanTask_02PinlvTime varchar(10) NULL,
	PlanTask_02Zhouqi int NULL,
	PlanTask_02ZhouqiStyle varchar(10) NULL,
	PlanTask_SMSWord varchar(50) NULL,
 CONSTRAINT PK_PlanTask PRIMARY KEY 
(
	PlanTask_ID 
)
) ;



/****** Object:  Table PD_PartInfo04    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE PD_PartInfo04(
	PartInfo_ID int NOT NULL,
	Part_ID int NULL,
	EDDY varchar(10) NULL,
	EDDL varchar(10) NULL,
 CONSTRAINT PK_PD_PartInfo04 PRIMARY KEY 
(
	PartInfo_ID 
)
) ;



/****** Object:  Table PD_PartInfo03    Script Date: 02/19/2014 15:15:49 ******/

CREATE TABLE PD_PartInfo03(
	PartInfo_ID int NOT NULL,
	Part_ID int NULL,
	JLLX int NULL,
	JLID varchar(10) NULL,
	GZPL decimal(9, 2) NULL,
	ZEDGL decimal(9, 2) NULL,
	AEDGL decimal(9, 2) NULL,
	BEDGL decimal(9, 2) NULL,
	CEDGL decimal(9, 2) NULL,
	EDDY decimal(9, 2) NULL,
	DYB decimal(9, 2) NULL,
	KZDL decimal(9, 2) NULL,
	KZSH decimal(9, 2) NULL,
	XL decimal(9, 2) NULL,
	ADYSX decimal(9, 2) NULL,
	BDYSX decimal(9, 2) NULL,
	CDYSX decimal(9, 2) NULL,
	ADYXX decimal(9, 2) NULL,
	BDYXX decimal(9, 2) NULL,
	CDYXX decimal(9, 2) NULL,
	AXEDL decimal(9, 2) NULL,
	BXEDL decimal(9, 2) NULL,
	CXEDL decimal(9, 2) NULL,
	ZXEWG decimal(9, 2) NULL,
	AXEWG decimal(9, 2) NULL,
	BXEWG decimal(9, 2) NULL,
	CXEWG decimal(9, 2) NULL,
	GLYS decimal(9, 2) NULL,
	Jiemian decimal(18, 0) NULL,
	Length int NULL,
	StartAddress varchar(100) NULL,
	EndAddress varchar(100) NULL,
	AutomaticCut int NULL,
	DLBalance decimal(18, 2) NULL,
	DYBalance decimal(18, 2) NULL,
	YGBalance decimal(18, 2) NULL,
	WGBalance decimal(18, 2) NULL,
 CONSTRAINT PK_PD_PartInfo03 PRIMARY KEY 
(
	PartInfo_ID 
)
) ;



/******
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'XEDL' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PD_PartInfo03', @level2type=N'COLUMN',@level2name=N'CXEDL'

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否自动关断' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'PD_PartInfo03', @level2type=N'COLUMN',@level2name=N'AutomaticCut'

/****** Object:  Table PD_PartInfo02    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE PD_PartInfo02(
	PartInfo_ID int NOT NULL,
	Part_ID int NULL,
	Man varchar(50) NULL,
	ManPhone varchar(50) NULL,
	GZPL varchar(10) NULL,
	EDGL varchar(10) NULL,
	EDDY varchar(10) NULL,
	DYB varchar(10) NULL,
	KZDL varchar(10) NULL,
	KZSH varchar(10) NULL,
	XL varchar(10) NULL,
	DYSX varchar(10) NULL,
	DYXX varchar(10) NULL,
	XEDL varchar(10) NULL,
	XEWG varchar(10) NULL,
	GLYS varchar(10) NULL,
 CONSTRAINT PK_PD_PartInfo02 PRIMARY KEY 
(
	PartInfo_ID 
)
) ;



/****** Object:  Table PD_PartInfo01    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE PD_PartInfo01(
	PartInfo_ID int NOT NULL,
	Part_ID int NULL,
	Address varchar(200) NULL,
	DYLevel varchar(100) NULL,
	XMLName varchar(100) NULL,
 CONSTRAINT PK_PartInfo PRIMARY KEY 
(
	PartInfo_ID 
)
); 



/****** Object:  Table PD_Part    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE PD_Part(
	Part_ID int NOT NULL,
	PartStyle_ID varchar(20) NULL,
	Part_Parent int NULL,
	State varchar(20) NULL,
	Part_Num varchar(20) NULL,
	Part_Name varchar(50) NULL,
	Part_SCCJ varchar(100) NULL,
	Part_TYRQ date NULL,
	Part_Remark varchar(200) NULL,
	Part_ParentLine int NULL,
	Part_IDs varchar(50) NULL,
 CONSTRAINT PK_PD_Part PRIMARY KEY 
(
	Part_ID 
)
); 



/****** Object:  Table PD_Over    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE PD_Over(
	PD_OverID int NOT NULL,
	Part_ID int NOT NULL,
	ValueTime date NOT NULL,
	OverStyle int NULL,
	ZValue decimal(18, 2) NULL,
	XValue decimal(18, 2) NULL,
	Remark varchar(100) NULL,
 CONSTRAINT PK_PD_Over PRIMARY KEY 
(
	PD_OverID 
)
) ;

/****** Object:  Table PD_Maintain    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE PD_Maintain(
	Maintain_ID int NOT NULL,
	Part_ID int NULL,
	Project varchar(20) NULL,
	MainDate date NULL,
	MainMan varchar(50) NULL,
	Remark varchar(200) NULL,
 CONSTRAINT PK_PD_Maintain PRIMARY KEY 
(
	Maintain_ID 
)
) ;



/****** Object:  Table PD_Balance    Script Date: 02/19/2014 15:15:49 ******/




CREATE TABLE PD_Balance(
	AmMeter_ID int NOT NULL,
	SelectYear int NOT NULL,
	SelectMonth int NOT NULL,
	SelectDay int NOT NULL,
	DLBalance decimal(18, 2) NULL,
	DYBalance decimal(18, 2) NULL,
	YGBalance decimal(18, 2) NULL,
	WGBalance decimal(18, 2) NULL,
 CONSTRAINT PK_PD_Balance PRIMARY KEY 
(
	AmMeter_ID ,
	SelectYear ,
	SelectMonth ,
	SelectDay 
)
) ;

/****** Object:  Table Partment    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE Partment(
	Partment_ID int NOT NULL,
	Partment_Num varchar(20) NULL,
	Partment_Name varchar(50) NULL,
	Partment_Parent int NULL,
	Partment_Man varchar(50) NULL,
	Partment_Phone varchar(20) NULL,
	Partment_Remark varchar(200) NULL,
	Partment_Order int NULL,
	Partment_Text varchar(200) NULL,
	Partment_IDs varchar(200) NULL,
 CONSTRAINT PK_Partment PRIMARY KEY 
(
	Partment_ID 
)
); 



/****** Object:  Table PageModel_User    Script Date: 02/19/2014 15:15:49 ******/




CREATE TABLE PageModel_User(
	PageModel_ID int NULL,
	User_ID int NULL
) ;

/****** Object:  Table PageModel    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE PageModel(
	PageModel_ID int NOT NULL,
	PageModel_Time date NULL,
	PageModel_Name varchar(50) NULL,
	PageModel_ConText varchar(500) NULL,
	PageModel_Num varchar(10) NULL,
 CONSTRAINT PK_PageModel PRIMARY KEY 
(
	PageModel_ID 
)
);



/****** Object:  Table PageMode    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE PageMode(
	PageMode_ID int NOT NULL,
	Power_Num varchar(50) NULL,
	PageMode_Name varchar(100) NULL,
	Users_ID int NULL,
	PageMode_Con varchar(500) NULL,
	Enable int NULL,
 CONSTRAINT PK_PageMode PRIMARY KEY 
(
	PageMode_ID 
)
) ;



/****** Object:  Table OprPartment_List    Script Date: 02/19/2014 15:15:49 ******/




CREATE TABLE OprPartment_List(
	Users_ID int NOT NULL,
	Partment_ID int NOT NULL,
 CONSTRAINT PK_OprPratment_List PRIMARY KEY 
(
	Users_ID ,
	Partment_ID 
)
) ;

/****** Object:  Table OprArea_List    Script Date: 02/19/2014 15:15:49 ******/




CREATE TABLE OprArea_List(
	Users_ID int NOT NULL,
	Area_ID int NOT NULL,
 CONSTRAINT PK_OprArea_List PRIMARY KEY 
(
	Users_ID ,
	Area_ID 
)
) ;

/****** Object:  Table OprArc_List    Script Date: 02/19/2014 15:15:49 ******/




CREATE TABLE OprArc_List(
	Users_ID int NOT NULL,
	Architecture_ID int NOT NULL,
	Area_ID int NULL,
 CONSTRAINT PK_OprArc_List PRIMARY KEY 
(
	Architecture_ID ,
	Users_ID 
)
) ;

/****** Object:  Table Module    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE Module(
	Module_ID numeric(18, 0) NOT NULL,
	Module_Name varchar(50) NULL,
	Module_Num varchar(4) NULL,
	Module_Address varchar(100) NULL,
	Module_Biaozhi varchar(50) NULL,
	Module_Parent int NULL,
	Module_Part1 varchar(20) NULL,
	Module_Part2 varchar(20) NULL,
	Module_Image varchar(100) NULL,
	Module_Remark varchar(250) NULL,
	Module_Order int NULL,
 CONSTRAINT PK_Module PRIMARY KEY 
(
	Module_ID 
)
); 



/****** Object:  Table MeteStyle    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE MeteStyle(
	MeteStyle_ID int NOT NULL,
	MeteStyle_Num varchar(20) NULL,
	MeteStyle_Name varchar(50) NULL,
	MeteStyle_Type varchar(10) NULL,
	MeteStyle_Changjia varchar(100) NULL,
	MeteStyle_time date NULL,
	MeteStyle_Remark varchar(250) NULL,
 CONSTRAINT PK_MeteStyle PRIMARY KEY 
(
	MeteStyle_ID 
)
) ;



/****** Object:  Table MeterTexing    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE MeterTexing(
	MeterTexing_ID int NOT NULL,
	MeterTexing_Name varchar(50) NULL,
	MeteStyle_Type varchar(10) NULL,
	MeterTexing_ShowType varchar(20) NULL,
	MeterTexing_ValueStyle varchar(50) NULL,
	MeterTexing_Remark varchar(50) NULL,
 CONSTRAINT PK_MeterTexing PRIMARY KEY 
(
	MeterTexing_ID 
)
) ;



/****** Object:  Table Message    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE Message(
	Message_ID int NOT NULL,
	Message_Title varchar(50) NULL,
	Message_Text varchar(200) NULL,
	Message_Time date NULL,
	Users_ID int NULL,
	Message_IsTop int NULL,
	Module_Num varchar(10) NULL,
	Man_ID int NULL,
	Message_State int NULL
); 



/****** Object:  Table ManualWaterMeter    Script Date: 02/19/2014 15:15:49 ******/
CREATE TABLE ManualWaterMeter(
	DataID int NOT NULL,
	Meter_ID int NOT NULL,
	ValueTime date NOT NULL,
	Area_ID int NULL,
	Architecture_ID int NULL,
	ZValue decimal(18, 2) NULL,
	ZGross decimal(18, 2) NULL,
	ZMoney decimal(18, 2) NULL,
	InsertTime date NULL,
	Users_ID int NULL,
 CONSTRAINT PK_MeterManualWater PRIMARY KEY 
(
	DataID 
)
) ;

/****** Object:  Table ManualMonth    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE ManualMonth(
	Manual_ID int NOT NULL,
	SelectYear int NULL,
	SelectMonth int NULL,
	Manual_inserttime date NULL,
	Manual_Man varchar(50) NULL,
	Architecture_ID int NULL,
	En07 decimal(18, 2) NULL,
	En10 decimal(18, 2) NULL,
	En11 decimal(18, 2) NULL,
	En12 decimal(18, 2) NULL,
 CONSTRAINT PK_ManualMonth PRIMARY KEY 
(
	Manual_ID 
)
); 



/****** Object:  Table ManualDay    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE ManualDay(
	Manual_ID int NOT NULL,
	SelectYear int NULL,
	SelectMonth int NULL,
	SelectDay int NULL,
	Manual_inserttime date NULL,
	Manual_Man varchar(50) NULL,
	Architecture_ID int NULL,
	En07 decimal(18, 2) NULL,
	En10 decimal(18, 2) NULL,
	En11 decimal(18, 2) NULL,
	En12 decimal(18, 2) NULL,
 CONSTRAINT PK_ManualDay PRIMARY KEY 
(
	Manual_ID 
)
); 



/****** Object:  Table ManualAmMeter    Script Date: 02/19/2014 15:15:49 ******/




CREATE TABLE ManualAmMeter(
	DataID int NOT NULL,
	Meter_ID int NOT NULL,
	ValueTime date NOT NULL,
	Area_ID int NULL,
	Architecture_ID int NULL,
	ZValue decimal(18, 2) NULL,
	ZGross decimal(18, 2) NULL,
	PowerFactorZ decimal(18, 5) NULL,
	ZMoney decimal(18, 2) NULL,
	InsertTime date NULL,
	Users_ID int NULL,
 CONSTRAINT PK_MeterManualAm PRIMARY KEY 
(
	DataID 
)
) ;

/****** Object:  Table LoginInfo    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE LoginInfo(
	LoginInfo_ID int NOT NULL,
	LoginInfo_IP varchar(50) NULL,
	LoginInfo_Result int NULL,
	Users_LoginName varchar(50) NULL,
	Users_ID int NULL,
	LoginInfo_Time date NULL
); 



/****** Object:  Table LogHistoryData    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE LogHistoryData(
	LogHistoryData_ID int NOT NULL,
	PlanTask_Style varchar(20) NULL,
	Exec_Time date NULL,
	Exec_Log varchar(100) NULL,
	Star_Time date NULL,
	End_Time date NULL,
	Exec_Opr int NULL,
 CONSTRAINT PK_LogHistoryData PRIMARY KEY 
(
	LogHistoryData_ID 
)
) ;



/****** Object:  Table LightRoomSchedule    Script Date: 02/19/2014 15:15:49 ******/




CREATE TABLE LightRoomSchedule(
	SwitchScheduleID numeric(18, 0) NOT NULL,
	Type int NOT NULL,
	Area_ID int NULL,
	Architecture_ID int NULL,
	Floor int NULL,
	LightRoomNo int NULL,
	yyyy int NOT NULL,
	mm int NOT NULL,
	dd int NOT NULL,
	Onhh int NOT NULL,
	Onmm int NOT NULL,
	Clearhh int NOT NULL,
	Clearmm int NOT NULL,
	Adjusthh int NOT NULL,
	Adjustmm int NOT NULL,
	Offhh int NOT NULL,
	Offmm int NOT NULL,
 CONSTRAINT PK_LightRoomSchedule PRIMARY KEY 
(
	SwitchScheduleID 
)
) ;

/****** Object:  Table LightRoom    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE LightRoom(
	LightRoomNo int NOT NULL,
	LightRoomName varchar(40) NOT NULL,
	Area_ID int NULL,
	Architecture_ID int NOT NULL,
	Floor int NOT NULL,
 CONSTRAINT PK_LightRoom PRIMARY KEY 
(
	LightRoomNo 
)
); 



/****** Object:  Table LightDeviceCtl    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE LightDeviceCtl(
	LightRoomNo int NOT NULL,
	DeviceNo int NOT NULL,
	Gather_ID int NOT NULL,
	Gather_PortNo int NOT NULL,
	BaudRate int NOT NULL,
	Parity char(1) NOT NULL,
	DataBit int NOT NULL,
	StopBit int NOT NULL,
	TimeOutUnit int NOT NULL,
	TimeOutTime int NOT NULL,
	ByteTimeOutTime int NOT NULL,
	SwitchMode int NOT NULL,
	LineSum int NOT NULL,
	CurrentLineOnSum int NOT NULL,
	MaxPeoplePerLine int NOT NULL,
	CurrentPeopleSum int NOT NULL,
	SwitchOnLightValue int NOT NULL,
	CurrentLightValue int NOT NULL,
	Stat int NOT NULL,
	IsOffAlarm int NOT NULL,
	LastTime date NULL,
 CONSTRAINT PK_LightDeviceCtl PRIMARY KEY 
(
	LightRoomNo ,
	DeviceNo 
)
) ;



/****** Object:  Table LableList    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE LableList(
	LableList_ID int NOT NULL,
	MapName varchar(20) NULL,
	Name varchar(20) NULL,
	imgUrl varchar(50) NULL,
	Color varchar(20) NULL,
	Show int NULL,
	TheOrder int NULL,
 CONSTRAINT PK_LableList PRIMARY KEY 
(
	LableList_ID 
)
); 



/****** Object:  Table LableInfo    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE LableInfo(
	LableList_ID int NULL,
	Date_ID int NULL,
	Remark varchar(200) NULL,
	PointX int NULL,
	PointY int NULL
); 



/****** Object:  Table InvoicePart    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE InvoicePart(
	InvoicePart_ID int NOT NULL,
	InvoiceNames varchar(50) NULL,
	bottomMargin int NULL,
	leftMargin int NULL,
	rightMargin int NULL,
	topMargin int NULL,
	defaultInvoice varchar(500) NULL,
	UserInvoice varchar(500) NULL,
 CONSTRAINT PK_InvoicePart PRIMARY KEY 
(
	InvoicePart_ID 
)
) ;



/****** Object:  Table Information    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE Information(
	Information_ID int NOT NULL,
	TDArea float NULL,
	JZArea float NULL,
	LHArea float NULL,
	BKManCount float NULL,
	YJManCount float NULL,
	BSManCount float NULL,
	ManCount float NULL,
	TheTime varchar(10) NULL
); 



/****** Object:  Table Gather    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE Gather(
	Gather_ID int NOT NULL,
	Area_ID int NULL,
	Architecture_ID int NULL,
	DataSite_ID int NULL,
	Gather_State int NULL,
	Gather_Num varchar(20) NULL,
	Gather_Name varchar(50) NULL,
	Gather_Address varchar(12) NULL,
	Gather_Password varchar(10) NULL,
	Gather_AnzhuangAddress varchar(200) NULL,
	Gather_Changshang varchar(50) NULL,
	Gather_Banben varchar(50) NULL,
	Gather_Size varchar(20) NULL,
	Protocol varchar(10) NULL,
	SendWay int NULL,
	LastTime date NULL,
	IP varchar(50) NULL,
	LastSetTime date NULL,
	LastSetMsg varchar(50) NULL,
	Gather_Style int NULL,
 CONSTRAINT PK_Gather PRIMARY KEY 
(
	Gather_ID 
)
); 



/****** Object:  Table GasMeter    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE GasMeter(
	GasMeter_ID int NOT NULL,
	Area_ID int NULL,
	Architecture_ID int NULL,
	Gather_ID int NULL,
	GasMeter_Num varchar(20) NULL,
	GasMeter_Name varchar(50) NULL,
	Feilv_ID int NULL,
	GasMeter_Password varchar(8) NULL,
	GasMeter_485Address varchar(12) NULL,
	Maker varchar(50) NULL,
	MakerCode varchar(50) NULL,
	AssetNo varchar(50) NULL,
	IsSupply int NOT NULL,
	ZValue decimal(18, 2) NOT NULL,
	UseAmStyle int NULL,
	ConsumerNum varchar(20) NULL,
	ConsumerName varchar(50) NULL,
	ConsumerPhone varchar(20) NULL,
	ConsumerAddress varchar(200) NULL,
	IsImportantUser int NULL,
	IsCountMeter int NULL,
	isComputation int NULL,
	Partment int NULL,
	Floor int NULL,
	MeteStyle_ID int NULL,
	Price_ID int NULL,
 CONSTRAINT PK_GasMeter PRIMARY KEY 
(
	GasMeter_ID 
)
); 



/****** Object:  Table FeilvList    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE FeilvList(
	FeilvList_ID int NOT NULL,
	Feilv_ID int NULL,
	FeilvList_name varchar(50) NULL,
	FeilvList_PeriodTime varchar(8) NULL,
	FeilvList_MinValue float NULL,
	FeilvList_MaxValue float NULL,
	FeilvList_Remark varchar(100) NULL,
 CONSTRAINT PK_FeilvList PRIMARY KEY 
(
	FeilvList_ID 
)
) ;



/****** Object:  Table Feilv    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE Feilv(
	Feilv_ID int NOT NULL,
	Feilv_Num varchar(10) NULL,
	Feilv_Name varchar(50) NULL,
	Feilv_Meter varchar(10) NULL,
	Feilv_Style varchar(20) NULL,
	Feilv_Remark varchar(200) NULL,
 CONSTRAINT PK_Feilv PRIMARY KEY 
(
	Feilv_ID 
)
) ;



/****** Object:  Table Estimate    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE Estimate(
	Estimate_ID int NOT NULL,
	Estimate_Index varchar(20) NULL,
	Estimate_Style int NULL,
	Estimate_Name varchar(200) NULL,
	Estimate_Year int NULL,
	Estimate_Count decimal(9, 2) NULL,
	Estimate_Value0 varchar(50) NULL,
	Estimate_Value1 varchar(50) NULL,
	Estimate_Value2 varchar(50) NULL,
	Estimate_Value3 varchar(50) NULL,
	Estimate_Value4 varchar(50) NULL,
	Estimate_Value5 varchar(50) NULL,
	Estimate_Value6 varchar(50) NULL,
	Estimate_Value7 varchar(50) NULL,
	Estimate_Value8 varchar(50) NULL,
	Estimate_Value9 varchar(50) NULL,
	Estimate_Value10 varchar(50) NULL,
	Estimate_Value11 varchar(50) NULL,
 CONSTRAINT PK_Estimate PRIMARY KEY 
(
	Estimate_ID 
)
) ;



/****** Object:  Table EnReportDataHis    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE EnReportDataHis(
	ID int NOT NULL,
	EnReportData_ID int NULL,
	ValueTime date NULL,
	EnReportData_Text varchar(500) NULL,
	EnReportData_Part varchar(1000) NULL,
	Style varchar(50) NULL,
	Result int NULL,
	Remark varchar(200) NULL,
 CONSTRAINT PK_EnReportDataHis PRIMARY KEY 
(
	ID 
)
) ;



/****** Object:  Table EnReportData    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE EnReportData(
	EnReportData_ID int NOT NULL,
	EnReportData_Num varchar(20) NULL,
	EnReportData_Name varchar(200) NULL,
	EnReportData_Enable int NULL,
	EnReportData_Style varchar(50) NULL,
	EnReportData_Text varchar(500) NULL,
	EnReportData_Part varchar(1000) NULL,
	EnReportData_Remark char(10) NULL,
 CONSTRAINT PK_EnReportData PRIMARY KEY 
(
	EnReportData_ID 
)
) ;



/******
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'EnReportData', @level2type=N'COLUMN',@level2name=N'EnReportData_ID'

/****** Object:  Table EnReport    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE EnReport(
	EnergyNum varchar(10) NOT NULL,
	EnergyName varchar(200) NULL,
	EnergyUnit varchar(20) NULL,
	Calorific decimal(10, 4) NULL,
	Coefficient decimal(18, 5) NULL,
	isManual int NULL,
	isReport int NULL,
 CONSTRAINT PK_EnReport PRIMARY KEY 
(
	EnergyNum 
)
) ;



/****** Object:  Table EnAuditArc    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE EnAuditArc(
	EnAuditArc_ID int NOT NULL,
	EnAuditArc_Num varchar(20) NULL,
	EnAuditArc_Name varchar(200) NULL,
	Architecture_ID int NULL,
	EnAuditArc_Year varchar(50) NULL,
	EnAuditArc_Time date NULL,
	EnAuditArc_FzMan varchar(200) NULL,
	EnAuditArc_ShMan varchar(200) NULL,
	EnAuditArc_Text varchar(500) NULL,
	EnAuditArc_Employers varchar(200) NULL,
	EnAuditArc_StarTime varchar(10) NULL,
	EnAuditArc_EndTime varchar(10) NULL,
	EnAuditArc_Img varchar(20) NULL,
 CONSTRAINT PK_EnAuditArc PRIMARY KEY 
(
	EnAuditArc_ID 
)
) ;


/****** Object:  Table DictionaryValue    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE DictionaryValue(
	DictionaryValue_ID int NOT NULL,
	Dictionary_ID int NULL,
	DictionaryValue_Num varchar(10) NOT NULL,
	DictionaryValue_Value varchar(50) NULL,
 CONSTRAINT PK_DICTIONARYVALUE PRIMARY KEY 
(
	DictionaryValue_ID 
)
); 



/****** Object:  Table Dictionary    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE Dictionary(
	Dictionary_ID int NOT NULL,
	Dictionary_Name varchar(30) NOT NULL,
	Dictionary_Remark varchar(250) NULL,
 CONSTRAINT PK_DICTIONARY PRIMARY KEY 
(
	Dictionary_ID 
)
); 



/****** Object:  Table DeptQutaDetlByPeoWater    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE DeptQutaDetlByPeoWater(
	ValueId int NOT NULL,
	Partment_ID int NOT NULL,
	Year varchar(4) NOT NULL,
	Name varchar(50) NULL,
	Price numeric(12, 2) NULL,
	M1 number(1) NULL,
	M2 number(1) NULL,
	M3 number(1) NULL,
	M4 number(1) NULL,
	M5 number(1) NULL,
	M6 number(1) NULL,
	M7 number(1) NULL,
	M8 number(1) NULL,
	M9 number(1) NULL,
	M10 number(1) NULL,
	M11 number(1) NULL,
	M12 number(1) NULL,
	TotalPeople int NULL,
	EStandard numeric(12, 2) NULL,
	Remark varchar(50) NULL,
 CONSTRAINT PK_DeptQutaDetlByPeoWater PRIMARY KEY 
(
	ValueId ,
	Partment_ID ,
	Year 
)
) ;



/****** Object:  Table DeptQutaDetlByPeo    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE DeptQutaDetlByPeo(
	ValueId int NOT NULL,
	Partment_ID int NOT NULL,
	Year varchar(4) NOT NULL,
	Name varchar(50) NULL,
	Price numeric(12, 2) NULL,
	M1 number(1) NULL,
	M2 number(1) NULL,
	M3 number(1) NULL,
	M4 number(1) NULL,
	M5 number(1) NULL,
	M6 number(1) NULL,
	M7 number(1) NULL,
	M8 number(1) NULL,
	M9 number(1) NULL,
	M10 number(1) NULL,
	M11 number(1) NULL,
	M12 number(1) NULL,
	TotalPeople int NULL,
	EStandard numeric(12, 2) NULL,
	Remark varchar(50) NULL,
 CONSTRAINT PK_DeptQutaDetlByPeo3 PRIMARY KEY 
(
	ValueId ,
	Partment_ID ,
	Year 
)
) ;



/****** Object:  Table DeptQutaDetlByDevWater    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE DeptQutaDetlByDevWater(
	ValueId int NOT NULL,
	Partment_ID int NOT NULL,
	Year varchar(4) NOT NULL,
	Name varchar(50) NULL,
	Price numeric(12, 2) NULL,
	M1 number(1) NULL,
	M2 number(1) NULL,
	M3 number(1) NULL,
	M4 number(1) NULL,
	M5 number(1) NULL,
	M6 number(1) NULL,
	M7 number(1) NULL,
	M8 number(1) NULL,
	M9 number(1) NULL,
	M10 number(1) NULL,
	M11 number(1) NULL,
	M12 number(1) NULL,
	TotalDevice int NULL,
	Power numeric(12, 2) NULL,
	RunHoursPerMth numeric(12, 2) NULL,
	Remark varchar(50) NULL,
 CONSTRAINT PK_DeptQutaDetlByDevWater PRIMARY KEY 
(
	ValueId ,
	Partment_ID ,
	Year 
)
); 



/****** Object:  Table DeptQutaDetlByDev    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE DeptQutaDetlByDev(
	ValueId int NOT NULL,
	Partment_ID int NOT NULL,
	Year varchar(4) NOT NULL,
	Name varchar(50) NULL,
	Price numeric(12, 2) NULL,
	M1 number(1) NULL,
	M2 number(1) NULL,
	M3 number(1) NULL,
	M4 number(1) NULL,
	M5 number(1) NULL,
	M6 number(1) NULL,
	M7 number(1) NULL,
	M8 number(1) NULL,
	M9 number(1) NULL,
	M10 number(1) NULL,
	M11 number(1) NULL,
	M12 number(1) NULL,
	TotalDevice int NULL,
	Power numeric(12, 2) NULL,
	RunHoursPerMth numeric(12, 2) NULL,
	Remark varchar(50) NULL,
 CONSTRAINT PK_DeptQutaDetlByDev2 PRIMARY KEY 
(
	ValueId ,
	Partment_ID ,
	Year 
)
); 



/****** Object:  Table DeptQuotaWater    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE DeptQuotaWater(
	Partment_ID int NOT NULL,
	Year varchar(4) NOT NULL,
	TiaozhengFixed numeric(12, 2) NULL,
	FixedZhuijia1 numeric(12, 2) NULL,
	FixedZhuijia2 numeric(12, 2) NULL,
	FixedZhuijia3 numeric(12, 2) NULL,
	FixedZhuijia4 numeric(12, 2) NULL,
	FixedZhuijia5 numeric(12, 2) NULL,
	FixedZhuijia6 numeric(12, 2) NULL,
	FixedZhuijia7 numeric(12, 2) NULL,
	FixedZhuijia8 numeric(12, 2) NULL,
	FixedZhuijia9 numeric(12, 2) NULL,
	FixedZhuijia10 numeric(12, 2) NULL,
	FixedZhuijia11 numeric(12, 2) NULL,
	FixedZhuijia12 numeric(12, 2) NULL,
 CONSTRAINT PK_DeptQuotaWater PRIMARY KEY 
(
	Partment_ID ,
	Year 
)
) ;



/****** Object:  Table DeptQuota    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE DeptQuota(
	Partment_ID int NOT NULL,
	Year varchar(4) NOT NULL,
	TiaozhengFixed numeric(12, 2) NULL,
	FixedZhuijia1 numeric(12, 2) NULL,
	FixedZhuijia2 numeric(12, 2) NULL,
	FixedZhuijia3 numeric(12, 2) NULL,
	FixedZhuijia4 numeric(12, 2) NULL,
	FixedZhuijia5 numeric(12, 2) NULL,
	FixedZhuijia6 numeric(12, 2) NULL,
	FixedZhuijia7 numeric(12, 2) NULL,
	FixedZhuijia8 numeric(12, 2) NULL,
	FixedZhuijia9 numeric(12, 2) NULL,
	FixedZhuijia10 numeric(12, 2) NULL,
	FixedZhuijia11 numeric(12, 2) NULL,
	FixedZhuijia12 numeric(12, 2) NULL,
 CONSTRAINT PK_DeptQuota PRIMARY KEY 
(
	Partment_ID ,
	Year 
)
) ;



/****** Object:  Table DeptFinalAccountsWater    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE DeptFinalAccountsWater(
	BillId int NOT NULL,
	SerialNo int NOT NULL,
	Year varchar(4) NOT NULL,
	StartMM int NOT NULL,
	EndMM int NOT NULL,
	StartYMD varchar(8) NOT NULL,
	EndYMD varchar(8) NOT NULL,
	LastSerialNo int NULL,
	Remark varchar(50) NOT NULL,
	userID int NOT NULL,
	systemtime date NOT NULL,
	MeterCnt int NOT NULL,
 CONSTRAINT PK_DeptFinalAccountsWater PRIMARY KEY 
(
	BillId ,
	SerialNo 
)
) ;



/****** Object:  Table DeptFinalAccounts    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE DeptFinalAccounts(
	BillId int NOT NULL,
	SerialNo int NOT NULL,
	Year varchar(4) NOT NULL,
	StartMM int NOT NULL,
	EndMM int NOT NULL,
	StartYMD varchar(8) NOT NULL,
	EndYMD varchar(8) NOT NULL,
	LastSerialNo int NULL,
	Remark varchar(50) NOT NULL,
	userID int NOT NULL,
	systemtime date NOT NULL,
	AmMeterCnt int NOT NULL,
 CONSTRAINT PK_DeptFinalAccounts2_1 PRIMARY KEY 
(
	BillId ,
	SerialNo 
)
) ;



/****** Object:  Table DeptBillSetWater    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE DeptBillSetWater(
	BillId int NOT NULL,
	Partment_ID int NOT NULL,
	BillName varchar(50) NULL,
 CONSTRAINT PK_DeptBillSetWater PRIMARY KEY 
(
	BillId ,
	Partment_ID 
)
) ;



/****** Object:  Table DeptBillSet    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE DeptBillSet(
	BillId int NOT NULL,
	Partment_ID int NOT NULL,
	BillName varchar(50) NULL,
 CONSTRAINT PK_DeptBillSet2 PRIMARY KEY 
(
	BillId ,
	Partment_ID 
)
); 



/****** Object:  Table DateTemperature    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE DateTemperature(
	ID int NOT NULL,
	DT_Date varchar(20) NULL,
	DT_Day int NULL,
	DT_Night int NULL
) ;



/****** Object:  Table DataSite    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE DataSite(
	DataSite_ID int NOT NULL,
	DataSite_Num varchar(20) NULL,
	DataSite_Name varchar(50) NULL,
	DataSite_IP varchar(20) NOT NULL,
	DataSite_Prot int NOT NULL,
	DataSite_Heart int NULL,
	DataSite_LastConTime varchar(20) NULL,
	DataSite_Remark varchar(200) NULL,
	DataSite_State int NULL,
 CONSTRAINT PK_DateSite PRIMARY KEY 
(
	DataSite_ID 
)
) ;



/****** Object:  Table DatabaseInfo    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE DatabaseInfo(
	DatabaseInfo_ID int NOT NULL,
	DatabaseInfo_Name varchar(50) NULL,
	DatabaseInfo_Server varchar(50) NULL,
	DatabaseInfo_User varchar(50) NULL,
	DatabaseInfo_PassWord varchar(50) NULL,
	DatabaseInfo_LastTime date NULL,
	DatabaseInfo_jiange int NULL,
	DatabaseInfo_Address varchar(200) NULL,
 CONSTRAINT PK_DatabaseInfo PRIMARY KEY 
(
	DatabaseInfo_ID 
)
); 



/****** Object:  Table CountMeterInfo    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE CountMeterInfo(
	CountMeterInfo_ID int NOT NULL,
	InfoStyle varchar(10) NULL,
	InfoID varchar(50) NULL,
	AmMeterList varchar(100) NULL,
	WaterMeterList varchar(100) NULL,
 CONSTRAINT PK_CountMeterInfo PRIMARY KEY 
(
	CountMeterInfo_ID 
)
); 



/****** Object:  Table ColumnInfo    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE ColumnInfo(
	ColumnInfo_ID int NOT NULL,
	ColumnInfo_Table varchar(50) NULL,
	ColumnInfo_name varchar(50) NULL,
	ColumnInfo_title varchar(50) NULL,
	ColumnInfo_shunxu int NULL,
	ColumnInfo_disable int NULL,
	ColumnInfo_type varchar(20) NULL,
 CONSTRAINT PK_Column PRIMARY KEY 
(
	ColumnInfo_ID 
)
) ;



/****** Object:  Table Book    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE Book(
	Book_ID int NOT NULL,
	Book_Num varchar(20) NULL,
	Book_Name varchar(50) NULL,
	Book_Style int NULL,
	Book_Sex int NULL,
	Book_Birth date NULL,
	Book_Phone varchar(20) NULL,
	Book_Priority int NULL,
 CONSTRAINT PK_Book PRIMARY KEY 
(
	Book_ID 
)
); 



/****** Object:  Table BldQutaDetlByPeoWater    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE BldQutaDetlByPeoWater(
	ValueId int NOT NULL,
	BldOrArea_ID int NOT NULL,
	AOrBflg number(1) NOT NULL,
	Year varchar(4) NOT NULL,
	Name varchar(50) NULL,
	Price numeric(12, 2) NULL,
	M1 number(1) NULL,
	M2 number(1) NULL,
	M3 number(1) NULL,
	M4 number(1) NULL,
	M5 number(1) NULL,
	M6 number(1) NULL,
	M7 number(1) NULL,
	M8 number(1) NULL,
	M9 number(1) NULL,
	M10 number(1) NULL,
	M11 number(1) NULL,
	M12 number(1) NULL,
	TotalPeople int NULL,
	EStandard numeric(12, 2) NULL,
	Remark varchar(50) NULL,
 CONSTRAINT PK_BldQutaDetlByPeoWater2 PRIMARY KEY 
(
	ValueId ,
	BldOrArea_ID ,
	Year 
)
); 



/****** Object:  Table BldQutaDetlByPeo    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE BldQutaDetlByPeo(
	ValueId int NOT NULL,
	BldOrArea_ID int NOT NULL,
	AOrBflg number(1) NOT NULL,
	Year varchar(4) NOT NULL,
	Name varchar(50) NULL,
	Price numeric(12, 2) NULL,
	M1 number(1) NULL,
	M2 number(1) NULL,
	M3 number(1) NULL,
	M4 number(1) NULL,
	M5 number(1) NULL,
	M6 number(1) NULL,
	M7 number(1) NULL,
	M8 number(1) NULL,
	M9 number(1) NULL,
	M10 number(1) NULL,
	M11 number(1) NULL,
	M12 number(1) NULL,
	TotalPeople int NULL,
	EStandard numeric(12, 2) NULL,
	Remark varchar(50) NULL,
 CONSTRAINT PK_BldQutaDetlByPeo2 PRIMARY KEY 
(
	ValueId ,
	BldOrArea_ID ,
	Year 
)
) ;



/****** Object:  Table BldQutaDetlByDevWater    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE BldQutaDetlByDevWater(
	ValueId int NOT NULL,
	BldOrArea_ID int NOT NULL,
	AOrBflg number(1) NOT NULL,
	Year varchar(4) NOT NULL,
	Name varchar(50) NULL,
	Price numeric(12, 2) NULL,
	M1 number(1) NULL,
	M2 number(1) NULL,
	M3 number(1) NULL,
	M4 number(1) NULL,
	M5 number(1) NULL,
	M6 number(1) NULL,
	M7 number(1) NULL,
	M8 number(1) NULL,
	M9 number(1) NULL,
	M10 number(1) NULL,
	M11 number(1) NULL,
	M12 number(1) NULL,
	TotalDevice int NULL,
	Power numeric(12, 2) NULL,
	RunHoursPerMth numeric(12, 2) NULL,
	Remark varchar(50) NULL,
 CONSTRAINT PK_BldQutaDetlByDevWater PRIMARY KEY 
(
	ValueId ,
	BldOrArea_ID ,
	Year 
)
); 



/****** Object:  Table BldQutaDetlByDev    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE BldQutaDetlByDev(
	ValueId int NOT NULL,
	BldOrArea_ID int NOT NULL,
	AOrBflg number(1) NOT NULL,
	Year varchar(4) NOT NULL,
	Name varchar(50) NULL,
	Price numeric(12, 2) NULL,
	M1 number(1) NULL,
	M2 number(1) NULL,
	M3 number(1) NULL,
	M4 number(1) NULL,
	M5 number(1) NULL,
	M6 number(1) NULL,
	M7 number(1) NULL,
	M8 number(1) NULL,
	M9 number(1) NULL,
	M10 number(1) NULL,
	M11 number(1) NULL,
	M12 number(1) NULL,
	TotalDevice int NULL,
	Power numeric(12, 2) NULL,
	RunHoursPerMth numeric(12, 2) NULL,
	Remark varchar(50) NULL,
 CONSTRAINT PK_BldQutaDetlByDev_1 PRIMARY KEY 
(
	ValueId ,
	BldOrArea_ID ,
	Year 
)
) ;



/****** Object:  Table BldQuotaWater    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE BldQuotaWater(
	BldOrArea_ID int NOT NULL,
	AOrBflg number(1) NOT NULL,
	Year varchar(4) NOT NULL,
	TiaozhengFixed numeric(12, 2) NULL,
	FixedZhuijia1 numeric(12, 2) NULL,
	FixedZhuijia2 numeric(12, 2) NULL,
	FixedZhuijia3 numeric(12, 2) NULL,
	FixedZhuijia4 numeric(12, 2) NULL,
	FixedZhuijia5 numeric(12, 2) NULL,
	FixedZhuijia6 numeric(12, 2) NULL,
	FixedZhuijia7 numeric(12, 2) NULL,
	FixedZhuijia8 numeric(12, 2) NULL,
	FixedZhuijia9 numeric(12, 2) NULL,
	FixedZhuijia10 numeric(12, 2) NULL,
	FixedZhuijia11 numeric(12, 2) NULL,
	FixedZhuijia12 numeric(12, 2) NULL,
 CONSTRAINT PK_BldQuotaWater PRIMARY KEY 
(
	BldOrArea_ID ,
	AOrBflg ,
	Year 
)
) ;



/****** Object:  Table BldQuota    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE BldQuota(
	BldOrArea_ID int NOT NULL,
	AOrBflg number(1) NOT NULL,
	Year varchar(4) NOT NULL,
	TiaozhengFixed numeric(12, 2) NULL,
	FixedZhuijia1 numeric(12, 2) NULL,
	FixedZhuijia2 numeric(12, 2) NULL,
	FixedZhuijia3 numeric(12, 2) NULL,
	FixedZhuijia4 numeric(12, 2) NULL,
	FixedZhuijia5 numeric(12, 2) NULL,
	FixedZhuijia6 numeric(12, 2) NULL,
	FixedZhuijia7 numeric(12, 2) NULL,
	FixedZhuijia8 numeric(12, 2) NULL,
	FixedZhuijia9 numeric(12, 2) NULL,
	FixedZhuijia10 numeric(12, 2) NULL,
	FixedZhuijia11 numeric(12, 2) NULL,
	FixedZhuijia12 numeric(12, 2) NULL,
 CONSTRAINT PK_BldQuota PRIMARY KEY 
(
	BldOrArea_ID ,
	AOrBflg ,
	Year 
)
) ;



/****** Object:  Table BldFinalAccountsWater    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE BldFinalAccountsWater(
	BillId int NOT NULL,
	SerialNo int NOT NULL,
	Year varchar(4) NOT NULL,
	StartMM int NOT NULL,
	EndMM int NOT NULL,
	StartYMD varchar(8) NOT NULL,
	EndYMD varchar(8) NOT NULL,
	LastSerialNo int NULL,
	Remark varchar(50) NOT NULL,
	userID int NOT NULL,
	systemtime date NOT NULL,
	MeterCnt int NOT NULL,
 CONSTRAINT PK_BldFinalAccountsWater PRIMARY KEY 
(
	BillId ,
	SerialNo 
)
) ;



/****** Object:  Table BldFinalAccounts    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE BldFinalAccounts(
	BillId int NOT NULL,
	SerialNo int NOT NULL,
	Year varchar(4) NOT NULL,
	StartMM int NOT NULL,
	EndMM int NOT NULL,
	StartYMD varchar(8) NOT NULL,
	EndYMD varchar(8) NOT NULL,
	LastSerialNo int NULL,
	Remark varchar(50) NOT NULL,
	userID int NOT NULL,
	systemtime date NOT NULL,
	AmMeterCnt int NOT NULL,
 CONSTRAINT PK_BldFinalAccounts PRIMARY KEY 
(
	BillId ,
	SerialNo 
)
) ;



/****** Object:  Table BldBillSetWater    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE BldBillSetWater(
	BillId int NOT NULL,
	BldOrArea_ID int NOT NULL,
	AOrBflg number(1) NOT NULL,
	BillName varchar(50) NULL,
 CONSTRAINT PK_BldBillSetWater PRIMARY KEY 
(
	BillId ,
	BldOrArea_ID 
)
) ;



/****** Object:  Table BldBillSet    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE BldBillSet(
	BillId int NOT NULL,
	BldOrArea_ID int NOT NULL,
	AOrBflg number(1) NOT NULL,
	BillName varchar(50) NULL,
 CONSTRAINT PK_BldBillSet PRIMARY KEY 
(
	BillId ,
	BldOrArea_ID 
)
) ;



/****** Object:  Table BaojingPart    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE BaojingPart(
	BaojingPart_ID int NOT NULL,
	BaojingPart_Index varchar(50) NULL,
	BaojingPart_Style int NULL,
	BaojingPart_Enable int NULL,
	BaojingPart_AmTime varchar(150) NULL,
	BaojingPart_WaterTime varchar(150) NULL,
	BaojingPart_AmBoDong float NULL,
	BaojingPart_WaterBoDong float NULL,
	BaojingPart_WaterLou float NULL,
	BaojingPart_AmGross float NULL,
	BaojingPart_WaterGross float NULL,
	BaojingPart_EnableSMS int NULL
) ;



/******
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'0全部，1区域，2建筑，3建筑类型，4楼层，5表计，6部门' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'BaojingPart', @level2type=N'COLUMN',@level2name=N'BaojingPart_Style'

/****** Object:  Table BaojingInfo    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE BaojingInfo(
	BaojingInfo_id int NOT NULL,
	BaojingInfo_Time date NULL,
	BaojingInfo_Style varchar(20) NULL,
	BaojingInfo_FL varchar(10) NULL,
	BaojingInfo_Index varchar(10) NULL,
	BaojingInfo_Title varchar(200) NULL,
	Module_Num varchar(10) NULL
) ;



/****** Object:  Table Area    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE Area(
	Area_ID numeric(18, 0) NOT NULL,
	Area_Num varchar(20) NOT NULL,
	Area_Name varchar(50) NULL,
	Area_Address varchar(80) NULL,
	Area_Phone varchar(20) NULL,
	Area_Remark varchar(250) NULL,
 CONSTRAINT PK_Area PRIMARY KEY 
(
	Area_ID 
)
) ;



/****** Object:  Table ArcMeterInfo    Script Date: 02/19/2014 15:15:49 ******/




CREATE TABLE ArcMeterInfo(
	Architecture_ID int NOT NULL,
	AmA int NULL,
	AmB int NULL,
	AmC int NULL,
	AmD int NULL,
	WaterA int NULL,
	WaterB int NULL,
	WaterC int NULL,
	WaterD int NULL,
	WaterG int NULL,
	WaterK int NULL,
 CONSTRAINT PK_ArcMeterInfo PRIMARY KEY 
(
	Architecture_ID 
)
) ;

/****** Object:  Table ArcManualEnergy    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE ArcManualEnergy(
	ManualEnergy_ID int NOT NULL,
	EnergyNum varchar(2) NULL,
	ValueTime date NULL,
	Area_ID int NULL,
	Architecture_ID int NULL,
	Gross decimal(18, 2) NULL,
 CONSTRAINT PK_ManualEnergyDatas PRIMARY KEY 
(
	ManualEnergy_ID 
)
) ;



/****** Object:  Table ArchitectureGL    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE ArchitectureGL(
	GL_ID int NOT NULL,
	Area_ID int NOT NULL,
	Architecture_ID int NULL,
	Floor int NULL,
	AmMeter_ID int NULL,
	GLZGross decimal(18, 2) NOT NULL,
	GLTime varchar(7) NOT NULL
) ;



/****** Object:  Table Architecture    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE Architecture(
	Architecture_ID int NOT NULL,
	Area_ID int NULL,
	Architecture_Num varchar(20) NOT NULL,
	Architecture_Name varchar(100) NULL,
	Architecture_Style varchar(5) NULL,
	Architecture_Time date NULL,
	Architecture_Storey int NULL,
	Architecture_Area float NULL,
	Architecture_Aircondition float NULL,
	Architecture_Function varchar(400) NULL,
	Architecture_FL varchar(5) NULL,
	Architecture_FloorStar int NULL,
	Architecture_imgUrl varchar(100) NULL,
	Architecture_AdvancePayment int NULL,
	Architecture_Man varchar(100) NULL,
	Architecture_Phone varchar(20) NULL,
	Architecture_ManCount int NULL,
	isEnReport int NULL,
 CONSTRAINT PK_Architecture PRIMARY KEY 
(
	Architecture_ID 
)
) ;



/******
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'建筑类别' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'Architecture', @level2type=N'COLUMN',@level2name=N'Architecture_Style'

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'建筑时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'Architecture', @level2type=N'COLUMN',@level2name=N'Architecture_Time'

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'楼层' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'Architecture', @level2type=N'COLUMN',@level2name=N'Architecture_Storey'

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'建筑面积' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'Architecture', @level2type=N'COLUMN',@level2name=N'Architecture_Area'

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'空调面积' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'Architecture', @level2type=N'COLUMN',@level2name=N'Architecture_Aircondition'

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'建筑功能' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'Architecture', @level2type=N'COLUMN',@level2name=N'Architecture_Function'

/****** Object:  Table AmStandbyModel    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE AmStandbyModel(
	AmMeter_ID int NOT NULL,
	StartTime varchar(8) NULL,
	EndTime varchar(8) NULL,
	LowLimit decimal(18, 2) NULL,
	UpperLimit decimal(18, 2) NULL,
	LastCheckTime date NULL,
 CONSTRAINT PK_AmStandbyCost PRIMARY KEY 
(
	AmMeter_ID 
)
) ;



/****** Object:  Table AmMeterSupply    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE AmMeterSupply(
	ValueId numeric(10, 0) NOT NULL,
	Users_ID int NULL,
	AmMeter_ID numeric(18, 0) NULL,
	ValueTime char(10) NULL,
	SupplyState int NULL,
	Style int NULL,
	Operate int NULL,
	TaskNum varchar(15) NULL
); 



/****** Object:  Table AmMeterPDErrorDatas    Script Date: 02/19/2014 15:15:49 ******/




CREATE TABLE AmMeterPDErrorDatas(
	AmMeter_ID int NOT NULL,
	ValueTime date NOT NULL,
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
	PowerSZZ decimal(18, 2) NULL,
	PowerSZA decimal(18, 2) NULL,
	PowerSZB decimal(18, 2) NULL,
	PowerSZC decimal(18, 2) NULL,
	ErrorCode int NULL
) ;

/****** Object:  Table AmMeterPDDatas    Script Date: 02/19/2014 15:15:49 ******/




CREATE TABLE AmMeterPDDatas(
	AmMeter_ID int NOT NULL,
	ValueTime date NOT NULL,
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
	PowerSZZ decimal(18, 2) NULL,
	PowerSZA decimal(18, 2) NULL,
	PowerSZB decimal(18, 2) NULL,
	PowerSZC decimal(18, 2) NULL,
 CONSTRAINT PK_AmMeterPDDatas PRIMARY KEY 
(
	AmMeter_ID ,
	ValueTime 
)
) ;

/******

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'AmMeterPDDatas', @level2type=N'COLUMN',@level2name=N'PowerSZA'

/****** Object:  Table AmMeterErrorDatas    Script Date: 02/19/2014 15:15:49 ******/




CREATE TABLE AmMeterErrorDatas(
	AmMeter_ID int NOT NULL,
	ValueTime date NOT NULL,
	ZValueZY decimal(18, 2) NULL,
	ErrorCode int NULL
); 

/****** Object:  Table AmMeterDatas    Script Date: 02/19/2014 15:15:49 ******/




CREATE TABLE AmMeterDatas(
	AmMeter_ID int NOT NULL,
	ValueTime date NOT NULL,
	ZValueZY decimal(18, 2) NOT NULL,
 CONSTRAINT PK_AmMeterDatas_1 PRIMARY KEY 
(
	AmMeter_ID ,
	ValueTime 
)
) ;

/****** Object:  Table AmMeter    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE AmMeter(
	AmMeter_ID int NOT NULL,
	AmMeter_Point int NULL,
	Area_ID int NULL,
	Architecture_ID int NULL,
	Gather_ID int NULL,
	AmMeter_Num varchar(20) NULL,
	AmMeter_Name varchar(50) NULL,
	AmMeter_Password varchar(8) NULL,
	AmMeter_485Address varchar(30) NULL,
	Maker varchar(50) NULL,
	MakerCode varchar(50) NULL,
	AssetNo varchar(50) NULL,
	IsSupply int NOT NULL,
	ZValue decimal(18, 2) NOT NULL,
	JValue decimal(18, 2) NOT NULL,
	FValue decimal(18, 2) NOT NULL,
	PValue decimal(18, 2) NOT NULL,
	GValue decimal(18, 2) NOT NULL,
	UseAmStyle int NULL,
	ConsumerNum varchar(20) NULL,
	ConsumerName varchar(50) NULL,
	ConsumerPhone varchar(20) NULL,
	ConsumerAddress varchar(200) NULL,
	IsImportantUser int NULL,
	IsCountMeter int NULL,
	isComputation int NULL,
	AmMeter_Plose int NULL,
	Partment int NULL,
	Floor int NULL,
	MeteStyle_ID int NULL,
	Price_ID int NULL,
	AmMeter_Stat int NULL,
	IsOffAlarm int NULL,
	CurPower decimal(18, 2) NULL,
	CostCheck int NULL,
	StandByCheck int NULL,
	Xiuzheng decimal(18, 2) NULL,
	LastTime date NULL,
	DataFrom int NULL,
	BeiLv decimal(10, 2) NULL,
 CONSTRAINT PK_AmMeter20120906 PRIMARY KEY 
(
	AmMeter_ID 
)
) ;



/****** Object:  Table AmMatchModel    Script Date: 02/19/2014 15:15:49 ******/




CREATE TABLE AmMatchModel(
	AmMeter_ID int NOT NULL,
	H0 decimal(18, 2) NULL,
	H1 decimal(18, 2) NULL,
	H2 decimal(18, 2) NULL,
	H3 decimal(18, 2) NULL,
	H4 decimal(18, 2) NULL,
	H5 decimal(18, 2) NULL,
	H6 decimal(18, 2) NULL,
	H7 decimal(18, 2) NULL,
	H8 decimal(18, 2) NULL,
	H9 decimal(18, 2) NULL,
	H10 decimal(18, 2) NULL,
	H11 decimal(18, 2) NULL,
	H12 decimal(18, 2) NULL,
	H13 decimal(18, 2) NULL,
	H14 decimal(18, 2) NULL,
	H15 decimal(18, 2) NULL,
	H16 decimal(18, 2) NULL,
	H17 decimal(18, 2) NULL,
	H18 decimal(18, 2) NULL,
	H19 decimal(18, 2) NULL,
	H20 decimal(18, 2) NULL,
	H21 decimal(18, 2) NULL,
	H22 decimal(18, 2) NULL,
	H23 decimal(18, 2) NULL,
	LastCheckTime date NULL,
 CONSTRAINT PK_AmModel PRIMARY KEY 
(
	AmMeter_ID 
)
) ;

/****** Object:  Table AmMaintenance    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE AmMaintenance(
	Maint_ID int NOT NULL,
	AmMeter_ID int NULL,
	MaintTime date NULL,
	MaintRemark varchar(2000) NULL,
	MaintMan varchar(50) NULL,
 CONSTRAINT PK_AmMaintenance PRIMARY KEY 
(
	Maint_ID 
)
) ;



/****** Object:  Table AmBaojing    Script Date: 02/19/2014 15:15:49 ******/






CREATE TABLE AmBaojing(
	AmBaojing_ID int NOT NULL,
	AmBaojing_Style int NULL,
	AmBaojing_Time date NULL,
	AmMeter_ID int NULL,
	AmBaojing_Remark varchar(200) NULL,
	AmBaojing_SendSMS int NULL,
	inserttime date NULL,
 CONSTRAINT PK_AmBaojing PRIMARY KEY 
(
	AmBaojing_ID 
)
) ;



/****** Object:  Table AmAbnormalZroe    Script Date: 02/19/2014 15:15:49 ******/




CREATE TABLE AmAbnormalZroe(
	ValueId int NOT NULL,
	AmMeter_ID int NULL,
	ValueTime date NULL,
	InsertTime date NULL,
	Again int NULL,
 CONSTRAINT PK_AmAbnormalZroe PRIMARY KEY 
(
	ValueId 
)
) ;

/****** Object:  Table AmAbnormalMiss    Script Date: 02/19/2014 15:15:49 ******/




CREATE TABLE AmAbnormalMiss(
	ValueId int NOT NULL,
	AmMeter_ID int NULL,
	ValueTime date NULL,
	InsertTime date NULL,
	Again int NULL,
 CONSTRAINT PK_AmAbnormalMiss PRIMARY KEY 
(
	ValueId 
)
) ;

/****** Object:  Table AmAbnormalDaozuo    Script Date: 02/19/2014 15:15:49 ******/




CREATE TABLE AmAbnormalDaozuo(
	ValueId int NOT NULL,
	AmMeter_ID int NULL,
	LastTime date NULL,
	ThisTime date NULL,
	LastZValueZY numeric(8, 2) NULL,
	ThisZValueZY numeric(8, 2) NULL,
	InsertTime date NULL,
 CONSTRAINT PK_AmAbnormalDaozuo PRIMARY KEY 
(
	ValueId 
)
) ;

/****** Object:  Table AmAbnormalBoDong    Script Date: 02/19/2014 15:15:49 ******/




CREATE TABLE AmAbnormalBoDong(
	ValueId int NOT NULL,
	AmMeter_ID int NULL,
	LastTime date NULL,
	ThisTime date NULL,
	LastGross numeric(8, 2) NULL,
	ThisGross numeric(8, 2) NULL,
	InsertTime date NULL,
 CONSTRAINT PK_AmAbnormalBoDong PRIMARY KEY 
(
	ValueId 
)
) ;

/******

/****** Object:  Default DF_AmAbnormalMiss_Again    Script Date: 02/19/2014 15:15:49 ******/
ALTER TABLE AmAbnormalMiss ADD  CONSTRAINT DF_AmAbnormalMiss_Again  DEFAULT (0) FOR Again

/****** Object:  Default DF_AmAbnormalZroe_Again    Script Date: 02/19/2014 15:15:49 ******/
ALTER TABLE AmAbnormalZroe ADD  CONSTRAINT DF_AmAbnormalZroe_Again  DEFAULT (0) FOR Again

/****** Object:  Default DF_AmMeter_AmMeter_Stat    Script Date: 02/19/2014 15:15:49 ******/
ALTER TABLE AmMeter ADD  CONSTRAINT DF_AmMeter_AmMeter_Stat  DEFAULT (0) FOR AmMeter_Stat

/****** Object:  Default DF_AmMeter_IsOffAlarm    Script Date: 02/19/2014 15:15:49 ******/
ALTER TABLE AmMeter ADD  CONSTRAINT DF_AmMeter_IsOffAlarm  DEFAULT (0) FOR IsOffAlarm

/****** Object:  Default DF_AmMeter_CurPower    Script Date: 02/19/2014 15:15:49 ******/
ALTER TABLE AmMeter ADD  CONSTRAINT DF_AmMeter_CurPower  DEFAULT (0) FOR CurPower

/****** Object:  Default DF_AmMeter_CostCheck    Script Date: 02/19/2014 15:15:49 ******/
ALTER TABLE AmMeter ADD  CONSTRAINT DF_AmMeter_CostCheck  DEFAULT ((-1)) FOR CostCheck

/****** Object:  Default DF_AmMeter_StandByCheck    Script Date: 02/19/2014 15:15:49 ******/
ALTER TABLE AmMeter ADD  CONSTRAINT DF_AmMeter_StandByCheck  DEFAULT (0 - 1) FOR StandByCheck

/****** Object:  Default DF_AmMeter_Xiuzheng    Script Date: 02/19/2014 15:15:49 ******/
ALTER TABLE AmMeter ADD  CONSTRAINT DF_AmMeter_Xiuzheng  DEFAULT (0) FOR Xiuzheng

/****** Object:  Default DF_AmMeter_DataFrom    Script Date: 02/19/2014 15:15:49 ******/
ALTER TABLE AmMeter ADD  CONSTRAINT DF_AmMeter_DataFrom  DEFAULT (0) FOR DataFrom

/****** Object:  Default DF_Architecture_Architecture_Storey    Script Date: 02/19/2014 15:15:49 ******/
ALTER TABLE Architecture ADD  CONSTRAINT DF_Architecture_Architecture_Storey  DEFAULT (1) FOR Architecture_Storey

/****** Object:  Default DF_Architecture_Architecture_FL    Script Date: 02/19/2014 15:15:49 ******/
ALTER TABLE Architecture ADD  CONSTRAINT DF_Architecture_Architecture_FL  DEFAULT ('F') FOR Architecture_FL

/****** Object:  Default DF_Architecture_Architecture_FloorStar    Script Date: 02/19/2014 15:15:49 ******/
ALTER TABLE Architecture ADD  CONSTRAINT DF_Architecture_Architecture_FloorStar  DEFAULT (1) FOR Architecture_FloorStar

/****** Object:  Default DF_Architecture_Architecture_AdvancePayment    Script Date: 02/19/2014 15:15:49 ******/
ALTER TABLE Architecture ADD  CONSTRAINT DF_Architecture_Architecture_AdvancePayment  DEFAULT (0) FOR Architecture_AdvancePayment

/****** Object:  Default DF_Architecture_isEnReport    Script Date: 02/19/2014 15:15:49 ******/
ALTER TABLE Architecture ADD  CONSTRAINT DF_Architecture_isEnReport  DEFAULT (0) FOR isEnReport

/****** Object:  Default DF_GLContrast_ZValue    Script Date: 02/19/2014 15:15:49 ******/
ALTER TABLE ArchitectureGL ADD  CONSTRAINT DF_GLContrast_ZValue  DEFAULT (0) FOR GLZGross

/****** Object:  Default DF_ColumnInfo_ColumnInfo_disable    Script Date: 02/19/2014 15:15:49 ******/
ALTER TABLE ColumnInfo ADD  CONSTRAINT DF_ColumnInfo_ColumnInfo_disable  DEFAULT (1) FOR ColumnInfo_disable

/****** Object:  Default DF_DataSite_DataSite_State    Script Date: 02/19/2014 15:15:49 ******/
ALTER TABLE DataSite ADD  CONSTRAINT DF_DataSite_DataSite_State  DEFAULT (0) FOR DataSite_State

/****** Object:  Default DF_GasMeter_GasMeter_ZValue    Script Date: 02/19/2014 15:15:49 ******/
ALTER TABLE GasMeter ADD  CONSTRAINT DF_GasMeter_GasMeter_ZValue  DEFAULT (0) FOR ZValue

/****** Object:  Default DF_GasMeter_isComputation    Script Date: 02/19/2014 15:15:49 ******/
ALTER TABLE GasMeter ADD  CONSTRAINT DF_GasMeter_isComputation  DEFAULT (1) FOR isComputation

/****** Object:  Default DF_Gather_Gather_State    Script Date: 02/19/2014 15:15:49 ******/
ALTER TABLE Gather ADD  CONSTRAINT DF_Gather_Gather_State  DEFAULT (0) FOR Gather_State

/****** Object:  Default DF__Gather__Gather_S__2138791E    Script Date: 02/19/2014 15:15:49 ******/
ALTER TABLE Gather ADD  DEFAULT (1) FOR Gather_Style

/****** Object:  Default DF_LableList_Show    Script Date: 02/19/2014 15:15:49 ******/
ALTER TABLE LableList ADD  CONSTRAINT DF_LableList_Show  DEFAULT (0) FOR Show

/****** Object:  Default DF_Module_Module_Order    Script Date: 02/19/2014 15:15:49 ******/
ALTER TABLE Module ADD  CONSTRAINT DF_Module_Module_Order  DEFAULT (0) FOR Module_Order

/****** Object:  Default DF_PD_PartInfo03_AutomaticCut    Script Date: 02/19/2014 15:15:49 ******/
ALTER TABLE PD_PartInfo03 ADD  CONSTRAINT DF_PD_PartInfo03_AutomaticCut  DEFAULT (0) FOR AutomaticCut

/****** Object:  Default DF_PublicituCon_IsFocus    Script Date: 02/19/2014 15:15:49 ******/
ALTER TABLE PublicityCon ADD  CONSTRAINT DF_PublicituCon_IsFocus  DEFAULT (0) FOR IsFocus

/****** Object:  Default DF_PublicituCon_IsTop    Script Date: 02/19/2014 15:15:49 ******/
ALTER TABLE PublicityCon ADD  CONSTRAINT DF_PublicituCon_IsTop  DEFAULT (0) FOR IsTop

/****** Object:  Default DF_SLKongzhi_SLKongzhi_State    Script Date: 02/19/2014 15:15:49 ******/
ALTER TABLE SLKongzhi ADD  CONSTRAINT DF_SLKongzhi_SLKongzhi_State  DEFAULT (0) FOR SLKongzhi_State

/****** Object:  Default DF_SLLamp_intPerCent    Script Date: 02/19/2014 15:15:49 ******/
ALTER TABLE SLLamp ADD  CONSTRAINT DF_SLLamp_intPerCent  DEFAULT (100) FOR intPerCent

/****** Object:  Default DF_Users_OnLine    Script Date: 02/19/2014 15:15:50 ******/
ALTER TABLE Users ADD  CONSTRAINT DF_Users_OnLine  DEFAULT (0) FOR OnLine

/****** Object:  Default DF_Users_isAlarm    Script Date: 02/19/2014 15:15:50 ******/
ALTER TABLE Users ADD  CONSTRAINT DF_Users_isAlarm  DEFAULT (1) FOR isAlarm

/****** Object:  Default DF_WaterMeter_WaterMeter_ZValue    Script Date: 02/19/2014 15:15:50 ******/
ALTER TABLE WaterMeter ADD  CONSTRAINT DF_WaterMeter_WaterMeter_ZValue  DEFAULT (0) FOR ZValue

/****** Object:  Default DF_WaterMeter_WaterMeter_Stat    Script Date: 02/19/2014 15:15:50 ******/
ALTER TABLE WaterMeter ADD  CONSTRAINT DF_WaterMeter_WaterMeter_Stat  DEFAULT (0) FOR WaterMeter_Stat

/****** Object:  Default DF_WaterMeter_CostCheck    Script Date: 02/19/2014 15:15:50 ******/
ALTER TABLE WaterMeter ADD  CONSTRAINT DF_WaterMeter_CostCheck  DEFAULT ((-1)) FOR CostCheck

/****** Object:  Default DF_WaterMeter_StandByCheck    Script Date: 02/19/2014 15:15:50 ******/
ALTER TABLE WaterMeter ADD  CONSTRAINT DF_WaterMeter_StandByCheck  DEFAULT ((-1)) FOR StandByCheck

/****** Object:  Default DF__WaterMete__WLeak__7465D2A7    Script Date: 02/19/2014 15:15:50 ******/
ALTER TABLE WaterMeter ADD  CONSTRAINT DF__WaterMete__WLeak__7465D2A7  DEFAULT ((-1)) FOR WLeakageCheck
******/
******/
/














