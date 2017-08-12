CREATE or replace procedure AddAmBaojing(
AmBaojing_Style int default 0,
AmBaojing_Time date,
AmMeter_ID int,
AmBaojing_Remark varchar,
AmBaojing_SendSMS int
)
as
cout int;
begin
select count(*) into cout from AmBaojing where AmBaojing_Style=AmBaojing_Style and AmBaojing_Time =AmBaojing_Time and AmMeter_ID=AmMeter_ID;
if cout=0 then
insert into AmBaojing(AmBaojing_Style,AmBaojing_Time,AmMeter_ID,AmBaojing_Remark,AmBaojing_SendSMS,inserttime)
values (AmBaojing_Style,AmBaojing_Time,AmMeter_ID,AmBaojing_Remark,AmBaojing_SendSMS,Sysdate);
end if;
end;




CREATE or replace procedure AddWaterBaojing
(
WaterBaojing_Style int default 0,
WaterBaojing_Time date,
WaterMeter_ID int,
WaterBaojing_Remark varchar,
WaterBaojing_SendSMS int
)
as
cout int;
begin
select count(*) into cout from WaterBaojing where WaterBaojing_Style=WaterBaojing_Style and WaterBaojing_Time=WaterBaojing_Time and WaterMeter_ID=WaterMeter_ID;
if cout=0 then
insert into WaterBaojing(WaterBaojing_Style,WaterBaojing_Time,WaterMeter_ID,WaterBaojing_Remark,WaterBaojing_SendSMS,inserttime)
values (WaterBaojing_Style,WaterBaojing_Time,WaterMeter_ID,WaterBaojing_Remark,WaterBaojing_SendSMS,sysdate);
end if;
end;




CREATE or replace PROCEDURE AmMatchModelEdit(
	AmMeter_Id int,
	CostCheck int,
	H0 number,
        H1 number,
	H2 number,
	H3 number,
	H4 number,
	H5 number,
	H6 number,
	H7 number,
	H8 number,
	H9 number,
	H10 number,
	H11 number,
	H12 number,
	H13 number,
	H14 number,
	H15 number,
	H16 number,
	H17 number,
	H18 number,
	H19 number,
	H20 number,
	H21 number,
	H22 number,
	H23 number
	
)
AS
begin
	delete from AmMatchModel where AmMeter_ID=AmMeter_Id;
	insert into AmMatchModel(AmMeter_ID,H0,H1,H2,H3,H4,H5,H6,H7,H8,H9,H10,H11,H12,H13,H14,H15,H16,H17,H18,H19,H20,H21,H22,H23)
	values(AmMeter_Id,H0,H1,H2,H3,H4,H5,H6,H7,H8,H9,H10,H11,H12,H13,H14,H15,H16,H17,H18,H19,H20,H21,H22,H23);

	update AmMeter set CostCheck=CostCheck where AmMeter_ID=AmMeter_id;
	
end;




CREATE OR REPLACE procedure AmMeterAdd
(
Area_ID int,
Architecture_ID int,
Gather_ID int,

AmMeter_Name varchar,
AmMeter_Password varchar,
AmMeter_485Address varchar,
Maker varchar,
MakerCode varchar,
AssetNo varchar,
IsSupply int,
ZValue number,
JValue number,
FValue number,
PValue number,
GValue number,
UseAmStyle int,
ConsumerNum varchar,
ConsumerName varchar,
ConsumerPhone varchar,
ConsumerAddress varchar,
IsImportantUser int,
IsCountMeter int,
isComputation int,
AmMeter_Plose int,
Partment int,
Floor int,
MeteStyle_ID int default 1,

Xiuzheng number default 0.00,
Price_ID int default 0,
DataFrom int default 0,
Beilv number default 1,
Result out int 
)
as
AmMeter_Point int default 0;
c int;
XZQDM  varchar(6);--行政区划代码编码
JZSBM  varchar(1);--建筑类别编码
JZZBM  varchar(1);--建筑类别一级子项编码
JZNum  varchar(3);-- 建筑识别编码
FLNHBM  varchar(2);-- 分类能耗编码
Ammeter_Num varchar(20);
begin
Result:=0;
if AmMeter_Point=0 then

	 c:=0;
	select nvl(max(AmMeter_Point),0) into c from  AmMeter where Gather_ID=Gather_ID;
	if (c=0) then
		 AmMeter_Point:=2;
	else
	 AmMeter_Point:=c+1;
        end if;
end if;
select count(AmMeter_ID) into c from AmMeter where (AmMeter_485Address=AmMeter_485Address or AmMeter_Point=AmMeter_Point) and Gather_ID=Gather_ID;

if c=0 then
 select nvl(SystemInfo_XZNum,'000000') into XZQDM from SystemInfo;
 select nvl(Architecture_Num,'001') into JZNum from Architecture where Architecture_ID=Architecture_ID;


if JZNum='' or JZNum is null then
 JZNum:='001'; end if;
if JZZBM='' or JZZBM is null then
 JZZBM:='a';end if;
if JZSBM='' or JZSBM is null then
 JZSBM:='F';end if;

 FLNHBM:='01';
 Ammeter_Num:=XZQDM||JZSBM||JZZBM||JZNum||FLNHBM||Ammeter_Num;

insert into AmMeter(Area_ID,Architecture_ID,Gather_ID,AmMeter_Num,AmMeter_Name,AmMeter_Password,AmMeter_485Address,AmMeter_Point,Maker,MakerCode,AssetNo,IsSupply,ZValue,JValue,FValue,PValue,GValue,UseAmStyle,ConsumerNum,ConsumerName,ConsumerPhone,ConsumerAddress,IsImportantUser,IsCountMeter,isComputation,AmMeter_Plose,Partment,Floor,MeteStyle_ID,Xiuzheng,Price_ID,CostCheck,IsOffAlarm,DataFrom,Beilv) 
values(Area_ID,Architecture_ID,Gather_ID,AmMeter_Num,AmMeter_Name,AmMeter_Password,AmMeter_485Address,AmMeter_Point,Maker,MakerCode,AssetNo,IsSupply,ZValue,JValue,FValue,PValue,GValue,UseAmStyle,ConsumerNum,ConsumerName,ConsumerPhone,ConsumerAddress,IsImportantUser,IsCountMeter,isComputation,AmMeter_Plose,Partment,Floor,MeteStyle_ID,Xiuzheng,Price_ID,-1,0,DataFrom,Beilv);
select max(AmMeter_ID) into Result from AmMeter;

end if;
end;




CREATE or replace  procedure AmMeterDel
(
AmMeter_ID int
)

as

begin
delete from  AmMeter where AmMeter_ID=AmMeter_ID ;
delete from T_MonthAm where AmMeter_ID=AmMeter_ID; 
delete from T_DayAm where AmMeter_ID=AmMeter_ID ;

delete from AmMeterDatas where AmMeter_ID=AmMeter_ID ;
delete from AmMeterPDDatas where AmMeter_ID=AmMeter_ID ;
delete from AmMeterErrorDatas where AmMeter_ID=AmMeter_ID ;
delete from AmMeterPDErrorDatas where AmMeter_ID=AmMeter_ID ;
end;



CREATE or replace procedure AmMeterEdit
(
AmMeter_ID int,
Area_ID int,
Architecture_ID int,
Gather_ID int,

AmMeter_Name varchar,
AmMeter_Password varchar,
AmMeter_485Address varchar,
Maker varchar,
MakerCode varchar,
AssetNo varchar,
IsSupply int,
ZValue number,
JValue number,
FValue number,
PValue number,
GValue number,
UseAmStyle int,
ConsumerNum varchar,
ConsumerName varchar,
ConsumerPhone varchar,
ConsumerAddress varchar,
IsImportantUser int,
IsCountMeter int,
isComputation int,
AmMeter_Plose int,
Partment int,
Floor int,
MeteStyle_ID int,
AmMeter_Point int default 0,
Xiuzheng number default 0.00,
Price_ID int default 0,
DataFrom int default 0,
Beilv number default 1,
Result out int 
)
as
 c int;
--生成能耗编码
  XZQDM  varchar(6);--行政区划代码编码
  JZSBM  varchar(1);--建筑类别编码
  JZZBM  varchar(1);--建筑类别一级子项编码
  JZNum  varchar(3);-- 建筑识别编码
  FLNHBM  varchar(2);-- 分类能耗编码
  AmMeter_Num varchar(20);
begin
Result:=0;
select count(AmMeter_ID) into c from AmMeter where (AmMeter_485Address=AmMeter_485Address or AmMeter_Point=AmMeter_Point) and Gather_ID=Gather_ID and  AmMeter_ID<>AmMeter_ID;
if c=0 then



select nvl(SystemInfo_XZNum,'000000') into XZQDM from SystemInfo;
select nvl(Architecture_Num,'001') into JZNum from Architecture where Architecture_ID=Architecture_ID;
select nvl(Architecture_Style,'a') into JZZBM from Architecture where Architecture_ID=Architecture_ID;
select nvl(Architecture_FL,'F') into JZSBM from Architecture where Architecture_ID=Architecture_ID;

if JZNum='' or JZNum is null then
 JZNum:='001'; end if;
if JZZBM='' or JZZBM is null then
 JZZBM:='a'; end if;
if JZSBM='' or JZSBM is null then
 JZSBM:='F'; end if;

 FLNHBM:='01';
Ammeter_Num:=XZQDM||JZSBM||JZZBM||JZNum||FLNHBM||Ammeter_Num;

	update AmMeter set Area_ID=Area_ID,
Architecture_ID=Architecture_ID,
Gather_ID=Gather_ID,
AmMeter_Num=AmMeter_Num,
AmMeter_Name=AmMeter_Name,
AmMeter_Password=AmMeter_Password,
AmMeter_485Address=AmMeter_485Address,
Maker=Maker,
MakerCode=MakerCode,
AssetNo=AssetNo,
IsSupply=IsSupply,
ZValue=ZValue,
JValue=JValue,
FValue=FValue,
PValue=PValue,
GValue=GValue,
UseAmStyle=UseAmStyle,
ConsumerNum=ConsumerNum,
ConsumerName=ConsumerName,
ConsumerPhone=ConsumerPhone,
ConsumerAddress=ConsumerAddress,
IsImportantUser=IsImportantUser,
IsCountMeter=IsCountMeter,
isComputation=isComputation,
AmMeter_Plose=AmMeter_Plose,
Partment=Partment,
Floor=Floor,
AmMeter_Point=AmMeter_Point,
MeteStyle_ID=MeteStyle_ID,
Xiuzheng=Xiuzheng,
Price_ID=Price_ID,
DataFrom=DataFrom,
Beilv =Beilv
	where AmMeter_ID=AmMeter_ID;
 Result:=AmMeter_ID;
end if;
end;




CREATE or replace procedure AmMeterPutin
(
ConsumerNum varchar,
ConsumerName varchar,
ConsumerPhone varchar,
Architecture varchar,
Floor int,
AmMeter_Name varchar,
AmMeterStyles varchar,
UseAmStyle varchar,
AssetNo varchar,
Maker varchar,
AmMeter_485Address varchar,
Gather_Address varchar,
AmMeter_Password varchar,
AmMeter_Num varchar,
Result out int 
)
as
 Area_ID int;
 Architecture_ID int;
 Gather_ID int;
 MeterStyle_ID int;
 AmMeter_Point int;
 cout int ;  --将count转为cout
begin
MeterStyle_ID:=1;
select max(MeteStyle_ID) into MeterStyle_ID from MeteStyle where MeteStyle_Name=AmMeterStyles and MeteStyle_Type='电表';
		
if Architecture<>''
then 
   if Gather_Address ='' or Gather_Address ='000000000000'
	then
		select count(Architecture_ID) into cout from Architecture where Architecture_Name=Architecture;
		if cout>0
		then
			select max(Architecture_ID) into Architecture_ID from Architecture where                                             Architecture_Name=Architecture;
			select Area_ID into Area_ID from Architecture where Architecture_ID=Architecture_ID;
		else
                         select Area_ID into Area_ID from (select * from Area order by Area_ID) where rownum=1 order                          by rownum asc;	
                   --exec ArchitectureAdd @Area_ID,'',@Architecture,'A',getdate,1,100,0,'',@Architecture_ID output		
		end if;
                Gather_ID:=0;	
else
           select count(Gather_ID) into cout from Gather where Gather_Address=Gather_Address;
           if cout>0
		then
			select max(Gather_ID) into Gather_ID from Gather where Gather_Address=Gather_Address;
			select Area_ID into Area_ID from Gather where Gather_ID=Gather_ID;
                        select Architecture_ID into Architecture_ID from Gather where Gather_ID=Gather_ID; 
		else
			select count(Architecture_ID) into cout from Architecture where                         Architecture_Name=Architecture;
                 if cout>0
			then
				select max(Architecture_ID) into Architecture_ID from Architecture where                                Architecture_Name=Architecture;
				select Area_ID into Area_ID from Architecture where Architecture_ID=Architecture_ID;
			else
			
				select Area_ID into Area_ID from (select * from Area order by Area_ID) where                                  rownum=1 order by rownum asc;	
                  --exec ArchitectureAdd @Area_ID,'',@Architecture,'A',getdate,1,100,0,'',@Architecture_ID output	
                  end if;
                end if;
  end if;
else
   if Gather_Address ='' or Gather_Address ='000000000000'
	then
		select max(Architecture_ID) into Architecture_ID from Architecture;
		select Area_ID into Area_ID from Architecture where Architecture_ID=Architecture_ID;
		 Gather_ID:=0;
	else
		select count(Gather_ID) into cout from Gather where Gather_Address=Gather_Address; 
		if cout>0
		then
			select max(Gather_ID) into Gather_ID from Gather where Gather_Address=Gather_Address; 
			select Area_ID into Area_ID from Gather where Gather_ID=Gather_ID ;
                        select Architecture_ID into Architecture_ID from Gather where Gather_ID=Gather_ID ;
		else
			select max(Architecture_ID) into Architecture_ID from Architecture;
			select Area_ID into Area_ID from Architecture where Architecture_ID=Architecture_ID;
          --vvvvvvvvvvvvv       
                end if;
      end if;
end if;
AmMeter_Point:=2;
 cout:=0;
select nvl(max(AmMeter_Point),0) into cout from  AmMeter where Gather_ID=Gather_ID;
if cout=0 then
 AmMeter_Point:=2;
else
 AmMeter_Point:=cout+1;
end if;
end;





CREATE or replace  Procedure AmMeterSelect
(
SelectWhere varchar default '',--默认第一层过滤
strGetFields varchar default  '*', 

orderFldDesc  varchar default '',--排序字段及排序方向，如addDate desc,id desc（排序字段需通过selectOrderFldName指定，可为空） 
PageSize     int default  10,           -- 页尺寸 ,如果为 0 不执行分页功能
PageIndex    int default  1          -- 页码 

)
as 
sql1 varchar(6000);  --sql转为sql1，由于sql为关键字
SelectWherer varchar(1500);--用来代替SelectWhere
strWhere     varchar(1000) default  '' ; -- 查询条件(注意: 不要加where) 
doCount int default  0;--返回记录总数, 非 0 值则返回
 keyFldName      varchar(80);   -- 主键字段 
begin
/*Gather_ID,Area_ID,Architecture_ID,DateSite_ID,Gather_State,Gather_Num,Gather_Name,Gather_Address,Gather_Password,Gather_AnzhuangAddress,Gather_Changshang,Gather_Banben,Gather_Size,Gather_Heart,Gather_MFrozen,Gather_DFrozen,Gather_Interval */
null;
 sql1:='';
SelectWherer:=SelectWhere;
if not(SelectWherer is null or rtrim(SelectWherer) = '' )  then
  SelectWherer:=' where '||SelectWherer;
end if;

if doCount=1 
then
	if not(strWhere is null or rtrim(strWhere) = '') then
                 strWhere:=' where '||strWhere;
                sql1:='select '||strGetFields||' from(select AmMeter_ID,(Select Area_Name from Area as a where T1.Area_ID=a.Area_ID) Area_Name,(Select Architecture_Name from Architecture as a where T1.Architecture_ID=a.Architecture_ID) Architecture_Name,(Select Gather_Name from Gather as a where T1.Gather_ID=a.Gather_ID) Gather_Name,AmMeter_Num,AmMeter_Name,AmMeter_Password,AmMeter_485Address,Maker,MakerCode,AssetNo,IsSupply,ZValue,JValue,FValue,PValue,GValue,(select DictionaryValue_Value as UseAmStyleName from  DictionaryValue where Dictionary_ID=6 and DictionaryValue_Num=T1.UseAmStyle)UseAmStyleName,ConsumerNum,ConsumerName,ConsumerPhone,ConsumerAddress,IsImportantUser,IsCountMeter,isComputation,AmMeter_Plose,(select Partment_Name from Partment  where Partment_ID=T1.Partment)PartmentName,Floor,(select MeteStyle_Name from MeteStyle as b where T1.MeteStyle_ID=b.MeteStyle_ID )MeteStyle_Name from (select * from AmMeter '||SelectWhere||')T1 )T2 '||strWhere;
         end if;
         if PageSize<>0
	 then
		
		keyFldName:='AmMeter_ID'; 
		--print sql
		--exec SearchGetDataByPage sql,orderFldDesc,keyFldName,PageSize,PageIndex
	
	else	 
		execute immediate sql1;
	end if;
else
       if strWhere is null or rtrim(strWhere) = ''
	then
		 sql1:='select Count(AmMeter_ID) from AmMeter '||SelectWhere;
	
	else
                 strWhere:=' where '||strWhere;
                 sql1:='select Count(AmMeter_ID) from(select AmMeter_ID,(Select Area_Name from Area as a where T1.Area_ID=a.Area_ID) Area_Name,(Select Architecture_Name from Architecture as a where T1.Architecture_ID=a.Architecture_ID) Architecture_Name,(Select Gather_Name from Gather as a where T1.Gather_ID=a.Gather_ID) Gather_Name,AmMeter_Num,AmMeter_Name,AmMeter_Password,AmMeter_485Address,Maker,MakerCode,AssetNo,IsSupply,ZValue,JValue,FValue,PValue,GValue,(select DictionaryValue_Value as UseAmStyleName from  DictionaryValue where Dictionary_ID=6 and DictionaryValue_Num=T1.UseAmStyle) UseAmStyleName,ConsumerNum,ConsumerName,ConsumerPhone,ConsumerAddress,IsImportantUser,IsCountMeter,isComputation,AmMeter_Plose,(select Partment_Name from Partment  where Partment_ID=T1.Partment)PartmentName,Floor,(select MeteStyle_Name from MeteStyle as b where T1.MeteStyle_ID=b.MeteStyle_ID )MeteStyle_Name from (select * from AmMeter '||SelectWhere||')T1 )T2 '||strWhere;
        end if;
        execute immediate sql1;
         
end if;
end;








CREATE or  replace  procedure ArchitectureAdd
(
Area_ID int,
Architecture_Num varchar,
Architecture_Name varchar,
Architecture_Style varchar,
Architecture_Time date,
Architecture_Storey int,
Architecture_Area float,
Architecture_Aircondition float,
Architecture_Function varchar,
Architecture_FloorStar int default 1,
Architecture_imgUrl varchar default 'upfile/Arc/ArcNone.gif',
Architecture_AdvancePayment int default 0,
Architecture_Man varchar,
Architecture_Phone  varchar,
Architecture_ManCount int default 0,
UserID int,
Result out int 
)
as
cout int;--关键字不可以为变量名，count->cout
error  int;--sqlserver中的全局变量
begin

 Result:=0;
select count(Architecture_ID) into cout from Architecture where Architecture_Num=Architecture_Num;
if cout=0 
then
insert into Architecture(Area_ID,Architecture_Num,Architecture_Name,Architecture_Style,Architecture_Time,Architecture_Storey,Architecture_Area,Architecture_Aircondition,Architecture_Function,Architecture_FloorStar,Architecture_imgUrl,Architecture_AdvancePayment,Architecture_Man,Architecture_Phone,Architecture_ManCount) 
	values(Area_ID,Architecture_Num,Architecture_Name,Architecture_Style,Architecture_Time,Architecture_Storey,Architecture_Area,Architecture_Aircondition,Architecture_Function,Architecture_FloorStar,Architecture_imgUrl,Architecture_AdvancePayment,Architecture_Man,Architecture_Phone,Architecture_ManCount);

if error =0
then
select max(Architecture_ID) into Result from Architecture;

if UserID<>1
then
insert into OprArc_List values(1,Result,Area_ID);
end if;
insert into OprArc_List values(UserID,Result,Area_ID);

end if;
 else
   Result:=0;
end if;

end;





CREATE or replace procedure ArchitectureDel
(
Architecture_ID int,
Result out int  
)
as
cout int;
begin
select count(*) into cout from AmMeter where Architecture_ID=Architecture_ID union all select WaterMeter_ID as Meter_ID from WaterMeter where Architecture_ID=Architecture_ID;
if cout !=0
then
 Result:=0;

else

delete from Architecture where Architecture_ID=Architecture_ID;
delete from OprArc_List where Architecture_ID=Architecture_ID;
delete from T_ArcDayAm where Architecture_ID=Architecture_ID;
delete from T_ArcDayGas where Architecture_ID=Architecture_ID;
delete from T_ArcDayWater where Architecture_ID=Architecture_ID;
delete from T_ArcFenleiDayAm where Architecture_ID=Architecture_ID;
delete from T_ArcFenleiDayGas where Architecture_ID=Architecture_ID;
delete from T_ArcFenleiDayWater where Architecture_ID=Architecture_ID;
delete from T_ArcStyleDayAm where Architecture_ID=Architecture_ID;
delete from T_ArcStyleDayGas where Architecture_ID=Architecture_ID;
delete from T_ArcStyleDayWater where Architecture_ID=Architecture_ID;
delete from LableInfo where Date_ID=Architecture_ID and LableList_ID=3;
 Result:=Architecture_ID;
end if;
end;





CREATE or replace procedure ArchitectureEdit
(
Architecture_ID int,
Area_ID int,
Architecture_Num varchar,
Architecture_Name varchar,
Architecture_Style varchar,
Architecture_Time date,
Architecture_Storey int,
Architecture_Area float,
Architecture_Aircondition float,
Architecture_Function varchar,
Architecture_FloorStar int default 1,
Architecture_imgUrl varchar default 'upfile/Arc/ArcNone.gif',
Architecture_AdvancePayment int default 0,
Architecture_Man varchar,
Architecture_Phone  varchar,
Architecture_ManCount int default 0,
Result out int 
)
as
cout int;
error int;--sqlserver里面的全局变量
begin

 Result:=0;
select count(Architecture_ID) into cout from Architecture where Architecture_Num=Architecture_Num and Architecture_ID<>Architecture_ID;
if cout=0
then
update Architecture set Area_ID=Area_ID,
Architecture_Num=Architecture_Num,
Architecture_Name=Architecture_Name,
Architecture_Style=Architecture_Style,
Architecture_Time=Architecture_Time,
Architecture_Storey=Architecture_Storey,
Architecture_Area=Architecture_Area,
Architecture_Aircondition=Architecture_Aircondition,
Architecture_Function=Architecture_Function,
Architecture_FL='F',
Architecture_FloorStar=Architecture_FloorStar,
Architecture_imgUrl=Architecture_imgUrl,
Architecture_AdvancePayment=Architecture_AdvancePayment,
Architecture_Man=Architecture_Man,
Architecture_Phone=Architecture_Phone,
Architecture_ManCount=Architecture_ManCount
 where Architecture_ID=Architecture_ID;

if error=0
then
 Result:=Architecture_ID;
else
Result:=0;

end if;
end if;
end;




CREATE or replace  procedure AreaAdd
(
Area_Num varchar,
Area_Name varchar,
Area_Address varchar,
Area_Phone varchar,
Area_Remark varchar,
UserID int,
Result out int 
)
as
error int;--模拟sqlserver里面的全局变量
begin

		insert into Area(Area_Num,Area_Name,Area_Address,Area_Phone,Area_Remark) 
			values(Area_Num,Area_Name,Area_Address,Area_Phone,Area_Remark);
                        if error =0
                   then
			
		select max(Area_ID) into Result from Area;
if UserID<>1
then
		insert into OprArea_List values(1,Result);
end if;
		insert into OprArea_List values(UserID,Result);
 else
   Result:=0;
end if;
end;


CREATE or replace procedure AreaDel
(
Area_ID int,
Result out int  
)
as
cout int;
begin
select  count(Architecture_ID) into cout from Architecture where Area_ID=Area_ID;
if cout <> 0
then
Result:=0;
else
delete from Area where Area_ID=Area_ID;--删除总库中的区域信息
delete from OprArea_List where Area_ID=Area_ID;
delete from OprArc_List where Area_ID=Area_ID;
 Result:=Area_ID;
end if;
end;





CREATE or replace procedure AreaEdit
(
Area_ID int,
Area_Num varchar,
Area_Name varchar,
Area_Address varchar,
Area_Phone varchar,
Area_Remark varchar,
Result out int  
)
as
error int;--模拟sqlserver里面的全局变量
begin

		update Area set Area_Num=Area_Num,
				Area_Name=Area_Name,
				Area_Address=Area_Address,
				Area_Phone=Area_Phone,
				Area_Remark=Area_Remark
		where Area_ID=Area_ID;
                if error =0 then
		 Result:=1;
              else
                          Result:=0;
               end if;
end;




create or replace procedure AutoUpSave
(
gatheraddress varchar,
metertype varchar,
meteraddress varchar,
commandcode varchar,
readtime date,
datavalue varchar,
Result out int  --原来是默认指定值为0，由于oracle不能如此设置，故去掉了default 0   
)
as
 meterid int;
 beilv number(10,2);
 Xiuzheng number(18,2);
 invalue number(18,2);
 Cnt int;				  --该表计已抄数据记录数
 ValueTime date;	  --
 ZValueZY number(18,2); --
begin

	
	 beilv:=1;
	 Xiuzheng:=0;

	-- 默认当电表来处理
	if metertype='1' or metertype='' or metertype is null 
            then
		select AmMeter_ID into meterid
		from VES_AmMeter where Gather_Address=gatheraddress and AmMeter_485Address=meteraddress;

                select nvl(beilv,1) into beilv
		from VES_AmMeter where Gather_Address=gatheraddress and AmMeter_485Address=meteraddress;

                select nvl(Xiuzheng,0) into Xiuzheng
		from VES_AmMeter where Gather_Address=gatheraddress and AmMeter_485Address=meteraddress;
	-- 水表
	elsif metertype='2'
		then
		/*  exec AutoUpSaveWater gatheraddress,metertype,meteraddress,commandcode,readtime,datavalue,Result out */
			return;
		
	-- 其它的时候，返回错误
	else
		        Result:=-1;
			return;
	end if;
	 if meterid=0
  then 
       Result:=-1;
      return;
  end if;
					
	--电表时只存了当前正向有功总电量
	--水表时只存了当前正向流量
	if commandcode='0C0110' or commandcode='0C0164' or commandcode='0D0264' or commandcode='' or commandcode is null 
		then
			  --declare SplitEqualPos int      --记录等号截取位置     
			  --declare dataname varchar(20)   --未使用	
			  --declare datavalue varchar(20)  --表计的示数
 
			  --select SplitEqualPos = CHARINDEX('=', datastr) 
			  --select dataname=LEFT(datastr, SplitEqualPos - 1)   
			  --select datavalue=RIGHT(datastr, Len(datastr)-SplitEqualPos)     

			  --考虑表计的倍率和换表调整数
			  
			   invalue:=to_number(datavalue,'9999999999999999.99')*beilv+Xiuzheng;
			  --print SplitEqualPos
			  --print datastr
			  --print datavalue
			  --print invalue
			  --print beilv
			  --print Xiuzheng
			  
			  --插入数据的时间要比当前时间小
			  if readtime > sysdate+1/24
				  then
					insert into AmMeterErrorDatas(AmMeter_ID,ValueTime,ZValueZY,ErrorCode) 
					values(meterid,readtime,to_number(invalue,'9999999999999999.99'),1);
					 Result:=1;
					return;
				  end if;

			 
			  
			   ZValueZY:=-1;
			  select ZValueZY into ZValueZY from
				( select * from ( select * from AmMeterDatas 
                            where AmMeter_ID=meterid  and ValueTime < readtime  order by ValueTime desc) 
                             where rownum =1 order by rownum asc
				) a;
			  if invalue<ZValueZY
				  then
					insert into AmMeterErrorDatas(AmMeter_ID,ValueTime,ZValueZY,ErrorCode) 
    					values(meterid,readtime,to_number(invalue,'9999999999999999.99'),2);
                                    --	2：插入的数据小于最后一次插入的数据
						 Result:=2;
						return;
				  end if;			
			
			   ZValueZY:=-1;
			  --select Cnt = count(AmMeter_ID) from AmMeterDatas where AmMeter_ID=meterid
			  --print Cnt
			  --if(Cnt = 0) --不存在数据
				--	begin
				--	  insert into AmMeterDatas(AmMeter_ID,ValueTime,ZValueZY) 
				--		  values(meterid,readtime,convert(decimal(18,2),invalue))
				--	  set Result=0
				--	  return
				--	end

			  select   count(AmMeter_ID) into Cnt from AmMeterDatas where AmMeter_ID=meterid and ValueTime = readtime;
			  --print Cnt
			  if Cnt > 0 --存在数据
					then
						insert into AmMeterErrorDatas(AmMeter_ID,ValueTime,ZValueZY,ErrorCode) 
				values(meterid,readtime,to_number(invalue,'9999999999999999.99'),3);
--	1：插入数据的时间重复
						 Result:=3;
						return;
					end if;

			  insert into AmMeterDatas(AmMeter_ID,ValueTime,ZValueZY) 
						values(meterid,readtime,to_number(invalue,'9999999999999999.99'));	
			   Result:=0;

		end if;	-- end of 当前正向有功总电量，正向流量
end; -- end of AutoUpSave












CREATE or replace procedure AutoUpSaveLonWorks
(
meteraddress varchar,
readtime date,
zyz varchar,
zya varchar,
zyb varchar,
zyc varchar,
PowerZY in out varchar, --因为要对powerZY进行重新赋值，所以只能设为 in out 类型的，下面的一样
PowerAY in out varchar,
PowerBY in out varchar,
PowerCY in out varchar,
PowerZW in out varchar,
PowerAW in out varchar,
PowerBW in out varchar,
PowerCW in out varchar,
PowerFactorZ in out varchar,
PowerFactorA in out varchar,
PowerFactorB in out varchar,
PowerFactorC in out varchar,
VoltageA in out varchar,
VoltageB in out varchar,
VoltageC in out varchar,
CurrentA in out varchar,
CurrentB in out varchar,
CurrentC in out varchar,
Current0 in out varchar,
Result out int  
)
as
 meterid int;
 Cnt int;
 sqlinsert varchar(5000);
 sqlvalue varchar(5000);
begin
  
  select AmMeter_ID into meterid from VES_AmMeter where AmMeter_485Address=meteraddress;

if(PowerZY!=-1) then
 PowerZY:=ABS(PowerZY);
end if;
if(PowerAY!=-1) then
 PowerAY:=ABS(PowerAY);
end if;
if(PowerBY!=-1) then
 PowerBY:=ABS(PowerBY);
end if;
if(PowerCY!=-1) then
 PowerCY:=ABS(PowerCY);
end if;
if(PowerZW!=-1) then
 PowerZW:=ABS(PowerZW);
end if;
if(PowerBW!=-1) then
 PowerBW:=ABS(PowerBW);
end if;
if(PowerCW!=-1) then
 PowerCW:=ABS(PowerCW);
end if;
if(PowerFactorZ!=-1) then
 PowerFactorZ:=ABS(PowerFactorZ);
end if;
if(PowerFactorA!=-1) then
 PowerFactorA:=ABS(PowerFactorA);
end if;
if(PowerFactorB!=-1) then
 PowerFactorB:=ABS(PowerFactorB);
end if;
if(PowerFactorC!=-1) then
 PowerFactorC:=ABS(PowerFactorC);
end if;
if(CurrentA!=-1) then
 CurrentA:=ABS(CurrentA);
end if;
if(CurrentB!=-1) then
 CurrentB:=ABS(CurrentB);
end if;
if(CurrentC!=-1) then
 CurrentC:=ABS(CurrentC);
end if;
if(Current0!=-1) then
 Current0:=ABS(Current0);
end if;
  --print(meterid)
  --插入数据的时间要比当前时间小
  if readtime >sysdate+1/24
  then
      insert into AmMeterErrorDatas(AmMeter_ID,ValueTime,ZValueZY,ErrorCode) 
      values(meterid,readtime,to_number(zyz,'9999999999999999.99'),1);
      insert into AmMeterPDErrorDatas(AmMeter_ID,ValueTime,PowerZY,PowerAY,PowerBY,PowerCY,PowerZW,PowerAW,PowerBW,PowerCW,PowerFactorZ,PowerFactorA,PowerFactorB,PowerFactorC,VoltageA,VoltageB,VoltageC,CurrentA,CurrentB,CurrentC,Current0,ErrorCode) 
      values(meterid,readtime,to_number(PowerZY,'9999999999999999.99'),to_number(PowerAY,'9999999999999999.99'),to_number(PowerBY,'9999999999999999.99'),to_number(PowerCY,'9999999999999999.99'),to_number(PowerZW,'9999999999999999.99'),to_number(PowerAW,'9999999999999999.99'),to_number(PowerBW,'9999999999999999.99'),to_number(PowerCW,'9999999999999999.99'),to_number(PowerFactorZ,'9999999999999999.99'),to_number(PowerFactorA,'9999999999999999.99'),to_number(PowerFactorB,'9999999999999999.99'),to_number(PowerFactorC,'9999999999999999.99'),to_number(VoltageA,'9999999999999999.99'),to_number(VoltageB,'9999999999999999.99'),to_number(VoltageC,'9999999999999999.99'),to_number(CurrentA,'9999999999999999.99'),to_number(CurrentB,'9999999999999999.99'),to_number(CurrentC,'9999999999999999.99'),to_number(Current0,'9999999999999999.99'),1);
      Result:=1;
      return;
  end if;

  				  --该表计已抄数据记录数
  select  count(AmMeter_ID) into Cnt from AmMeterPDDatas where AmMeter_ID=meterid and ValueTime = readtime; 
  --print(Cnt)
 
  --declare sqlupdate varchar(5000)
   sqlinsert:='insert into AmMeterPDDatas(AmMeter_ID,ValueTime';
   sqlvalue:='values('||to_char(meterid)||','''||to_char(readtime,'yyyy-mm-dd hh24:mi:ss')||'''';
  --set sqlupdate=''
  if PowerZY <>''
    then 
       sqlinsert:=sqlinsert||',PowerZY';
       sqlvalue:=sqlvalue||','||to_char(PowerZY);
      --set sqlupdate=sqlupdate||',PowerZY='||PowerZY
    end if;
  if PowerAY <>''
    then 
       sqlinsert:=sqlinsert||',PowerAY';
       sqlvalue:=sqlvalue||','||to_char(PowerAY);
      --set sqlupdate=sqlupdate||',PowerAY='||PowerAY
    end if;
  if PowerBY <>''
    then 
       sqlinsert:=sqlinsert||',PowerBY';
       sqlvalue:=sqlvalue||','||to_char(PowerBY);
      --set sqlupdate=sqlupdate||',PowerBY='||PowerBY
    end if;
  if PowerCY <>''
    then 
       sqlinsert:=sqlinsert||',PowerCY';
       sqlvalue:=sqlvalue||','||to_char(PowerCY);
      --set sqlupdate=sqlupdate||',PowerCY='||PowerCY
    end if;
  if PowerZW <>''
    then 
       sqlinsert:=sqlinsert||',PowerZW';
       sqlvalue:=sqlvalue||','||to_char(PowerZW);
      --set sqlupdate=sqlupdate||',PowerZW='||PowerZW
    end if;
  if PowerAW <>''
    then 
       sqlinsert:=sqlinsert||',PowerAW';
       sqlvalue:=sqlvalue||','||to_char(PowerAW);
      --set sqlupdate=sqlupdate||',PowerAW='||PowerAW
    end if;
  if PowerBW <>''
    then 
       sqlinsert:=sqlinsert||',PowerBW';
       sqlvalue:=sqlvalue||','||to_char(PowerBW);
      --set sqlupdate=sqlupdate||',PowerBW='||PowerBW
    end if;
  if PowerCW <>''
    then 
       sqlinsert:=sqlinsert||',PowerCW';
       sqlvalue:=sqlvalue||','||to_char(PowerCW);
      --set sqlupdate=sqlupdate||',PowerCW='||PowerCW
    end if;
  if PowerFactorZ <>''
    then 
       sqlinsert:=sqlinsert||',PowerFactorZ';
       sqlvalue:=sqlvalue||','||to_char(PowerFactorZ);
      --set sqlupdate=sqlupdate||',PowerFactorZ='||PowerFactorZ
    end if;
  if PowerFactorA <>''
    then 
       sqlinsert:=sqlinsert||',PowerFactorA';
       sqlvalue:=sqlvalue||','||to_char(PowerFactorA);
      --set sqlupdate=sqlupdate||',PowerFactorA='||PowerFactorA
    end if;
  if PowerFactorB <>''
    then 
       sqlinsert:=sqlinsert||',PowerFactorB';
       sqlvalue:=sqlvalue||','||to_char(PowerFactorB);
      --set sqlupdate=sqlupdate||',PowerFactorB='||PowerFactorB
    end if;
  if PowerFactorC <>''
    then 
       sqlinsert:=sqlinsert||',PowerFactorC';
       sqlvalue:=sqlvalue||','||to_char(PowerFactorC);
      --set sqlupdate=sqlupdate||',PowerFactorC='||PowerFactorC
    end if;
  if VoltageA <>''
    then 
       sqlinsert:=sqlinsert||',VoltageA';
       sqlvalue:=sqlvalue||','||to_char(VoltageA);
      --set sqlupdate=sqlupdate||',VoltageA='||VoltageA
    end if;
  if VoltageB <>''
    then 
       sqlinsert:=sqlinsert||',VoltageB';
       sqlvalue:=sqlvalue||','||to_char(VoltageB);
      --set sqlupdate=sqlupdate||',VoltageB='||VoltageB
    end if;
  if VoltageC <>''
    then 
       sqlinsert:=sqlinsert||',VoltageC';
       sqlvalue:=sqlvalue||','||to_char(VoltageC);
      --set sqlupdate=sqlupdate||',VoltageC='||VoltageC
    end if;
  if CurrentA <>''
    then 
       sqlinsert:=sqlinsert||',CurrentA';
       sqlvalue:=sqlvalue||','||to_char(CurrentA);
      --set sqlupdate=sqlupdate||',CurrentA='||CurrentA
    end if;
  if CurrentB <>''
    then 
       sqlinsert:=sqlinsert||',CurrentB';
       sqlvalue:=sqlvalue||','||to_char(CurrentB);
      --set sqlupdate=sqlupdate||',CurrentB='||CurrentB
    end if;
  if CurrentC <>''
    then 
       sqlinsert:=sqlinsert||',CurrentC';
       sqlvalue:=sqlvalue||','||to_char(CurrentC);
      --set sqlupdate=sqlupdate||',CurrentC='||CurrentC
    end if;
  if Current0 <>''
    then 
       sqlinsert:=sqlinsert||',Current0';
       sqlvalue:=sqlvalue||','||to_char(Current0);
      --set sqlupdate=sqlupdate||',Current0='||Current0
    end if;
   sqlinsert:=sqlinsert||')';
   sqlvalue:=sqlvalue||')';
  
  if Cnt = 0 --不存在数据
    then
      --print(sqlinsert||sqlvalue)
      execute immediate sqlinsert||sqlvalue;
    end if;
  --else
   --   if sqlupdate<>''
     -- begin
       -- set sqlupdate='update AmMeterPDDatas set '||substring(sqlupdate,2,len(sqlupdate)-1)||' where AmMeter_ID='||convert(char(10),meterid)||' and ValueTime = '''||CONVERT(CHAR(19), readtime, 120)||''''
        --print(sqlupdate)
        --exec(sqlupdate)
      --end
select  count(AmMeter_ID) into Cnt from AmMeterDatas where AmMeter_ID=meterid and ValueTime = readtime; --电量
if Cnt = 0 --不存在数据
then
insert into AmMeterDatas(AmMeter_ID,ValueTime,ZValueZY) 
values(meterid,readtime,to_number(zyz,'9999999999999999.99'));	
end if;
       Result:=0;
end; -- end of AutoUpSaveThreePhase









CREATE or replace procedure AutoUpSaveThreePhase
(
gatheraddress varchar,
meteraddress varchar,
readtime date,
PowerZY in out varchar,--因为要对powerZY进行重新赋值，所以只能设为 in out 类型的，下面的一样
PowerAY in out varchar,
PowerBY in out varchar,
PowerCY in out varchar,
PowerZW in out varchar,
PowerAW in out varchar,
PowerBW in out varchar,
PowerCW in out varchar,
PowerFactorZ in out varchar,
PowerFactorA in out varchar,
PowerFactorB in out varchar,
PowerFactorC in out varchar,
VoltageA in out varchar,
VoltageB in out varchar,
VoltageC in out varchar,
CurrentA in out varchar,
CurrentB in out varchar,
CurrentC in out varchar,
Current0 in out varchar,
PowerSZZ in out varchar,
PowerSZA in out varchar,
PowerSZB in out varchar,
PowerSZC in out varchar,
Result out int 
)
as
 meterid int;
 Cnt int;
 sqlinsert varchar(5000);
 sqlvalue varchar(5000);				  --该表计已抄数据记录数
begin
 meterid:=0;
  select AmMeter_ID into meterid from VES_AmMeter where Gather_Address=gatheraddress and AmMeter_485Address=meteraddress;
  --print (meterid)
  if meterid=0
  then 
       Result:=-1;
      return;
  end if;
if(PowerZY!=-1) then
 PowerZY:=ABS(PowerZY);
end if;
if(PowerAY!=-1) then
 PowerAY:=ABS(PowerAY);
end if;
if(PowerBY!=-1) then
 PowerBY:=ABS(PowerBY);
end if;
if(PowerCY!=-1) then
 PowerCY:=ABS(PowerCY);
end if;
if(PowerZW!=-1) then
 PowerZW:=ABS(PowerZW);
end if;
if(PowerBW!=-1) then
 PowerBW:=ABS(PowerBW);
end if;
if(PowerCW!=-1) then
 PowerCW:=ABS(PowerCW);
end if;
if(PowerFactorZ!=-1) then
 PowerFactorZ:=ABS(PowerFactorZ);
end if;
if(PowerFactorA!=-1) then
 PowerFactorA:=ABS(PowerFactorA);
end if;
if(PowerFactorB!=-1) then
 PowerFactorB:=ABS(PowerFactorB);
end if;
if(PowerFactorC!=-1) then
 PowerFactorC:=ABS(PowerFactorC);
end if;
if(CurrentA!=-1) then
 CurrentA:=ABS(CurrentA);
end if;
if(CurrentB!=-1) then
 CurrentB:=ABS(CurrentB);
end if;
if(CurrentC!=-1) then
 CurrentC:=ABS(CurrentC);
end if;
if(Current0!=-1) then
 Current0:=ABS(Current0);
end if;
 --插入数据的时间要比当前时间小
  if readtime >sysdate+1/24
  then
      insert into AmMeterPDErrorDatas(AmMeter_ID,ValueTime,PowerZY,PowerAY,PowerBY,PowerCY,PowerZW,PowerAW,PowerBW,PowerCW,PowerFactorZ,PowerFactorA,PowerFactorB,PowerFactorC,VoltageA,VoltageB,VoltageC,CurrentA,CurrentB,CurrentC,Current0,PowerSZZ,PowerSZA,PowerSZB,PowerSZC,ErrorCode) 
      values(meterid,readtime,to_number(PowerZY,'9999999999999999.99'),to_number(PowerAY,'9999999999999999.99'),to_number(PowerBY,'9999999999999999.99'),to_number(PowerCY,'9999999999999999.99'),to_number(PowerZW,'9999999999999999.99'),to_number(PowerAW,'9999999999999999.99'),to_number(PowerBW,'9999999999999999.99'),to_number(PowerCW,'9999999999999999.99'),to_number(PowerFactorZ,'9999999999999999.99'),to_number(PowerFactorA,'9999999999999999.99'),to_number(PowerFactorB,'9999999999999999.99'),to_number(PowerFactorC,'9999999999999999.99'),to_number(VoltageA,'9999999999999999.99'),to_number(VoltageB,'9999999999999999.99'),to_number(VoltageC,'9999999999999999.99'),to_number(CurrentA,'9999999999999999.99'),to_number(CurrentB,'9999999999999999.99'),to_number(CurrentC,'9999999999999999.99'),to_number(Current0,'9999999999999999.99'),to_number(PowerSZZ,'9999999999999999.99'),to_number(PowerSZA,'9999999999999999.99'),to_number(PowerSZB,'9999999999999999.99'),to_number(PowerSZC,'9999999999999999.99'),1);
       Result:=1;
      return;
  end if;
select  count(AmMeter_ID) into Cnt from AmMeterPDDatas where AmMeter_ID=meterid and ValueTime = readtime ;
  --print(Cnt)

  --declare sqlupdate varchar(5000)
   sqlinsert:='insert into AmMeterPDDatas(AmMeter_ID,ValueTime';
   sqlvalue:='values('||to_char(meterid)||','''||to_char(readtime,'yyyy-mm-dd hh24:mi:ss')||'''';
  --set sqlupdate=''
  if PowerZY <>''
    then 
       sqlinsert:=sqlinsert||',PowerZY';
       sqlvalue:=sqlvalue||','||to_char(PowerZY);
      --set sqlupdate=sqlupdate||',PowerZY='||PowerZY
    end if;
  if PowerAY <>''
    then 
       sqlinsert:=sqlinsert||',PowerAY';
       sqlvalue:=sqlvalue||','||to_char(PowerAY);
      --set sqlupdate=sqlupdate||',PowerAY='||PowerAY
    end if;
  if PowerBY <>''
    then 
       sqlinsert:=sqlinsert||',PowerBY';
       sqlvalue:=sqlvalue||','||to_char(PowerBY);
      --set sqlupdate=sqlupdate||',PowerBY='||PowerBY
    end if;
  if PowerCY <>''
    then 
       sqlinsert:=sqlinsert||',PowerCY';
       sqlvalue:=sqlvalue||','||to_char(PowerCY);
      --set sqlupdate=sqlupdate||',PowerCY='||PowerCY
    end if;
  if PowerZW <>''
    then 
       sqlinsert:=sqlinsert||',PowerZW';
       sqlvalue:=sqlvalue||','||to_char(PowerZW);
      --set sqlupdate=sqlupdate||',PowerZW='||PowerZW
    end if;
  if PowerAW <>''
    then 
       sqlinsert:=sqlinsert||',PowerAW';
       sqlvalue:=sqlvalue||','||to_char(PowerAW);
      --set sqlupdate=sqlupdate||',PowerAW='||PowerAW
    end if;
  if PowerBW <>''
    then 
       sqlinsert:=sqlinsert||',PowerBW';
       sqlvalue:=sqlvalue||','||to_char(PowerBW);
      --set sqlupdate=sqlupdate||',PowerBW='||PowerBW
    end if;
  if PowerCW <>''
    then 
       sqlinsert:=sqlinsert||',PowerCW';
       sqlvalue:=sqlvalue||','||to_char(PowerCW);
      --set sqlupdate=sqlupdate||',PowerCW='||PowerCW
    end if;
  if PowerFactorZ <>''
    then 
       sqlinsert:=sqlinsert||',PowerFactorZ';
       sqlvalue:=sqlvalue||','||to_char(PowerFactorZ);
      --set sqlupdate=sqlupdate||',PowerFactorZ='||PowerFactorZ
    end if;
  if PowerFactorA <>''
    then 
       sqlinsert:=sqlinsert||',PowerFactorA';
       sqlvalue:=sqlvalue||','||to_char(PowerFactorA);
      --set sqlupdate=sqlupdate||',PowerFactorA='||PowerFactorA
    end if;
  if PowerFactorB <>''
    then 
       sqlinsert:=sqlinsert||',PowerFactorB';
       sqlvalue:=sqlvalue||','||to_char(PowerFactorB);
      --set sqlupdate=sqlupdate||',PowerFactorB='||PowerFactorB
    end if;
  if PowerFactorC <>''
    then 
       sqlinsert:=sqlinsert||',PowerFactorC';
       sqlvalue:=sqlvalue||','||to_char(PowerFactorC);
      --set sqlupdate=sqlupdate||',PowerFactorC='||PowerFactorC
    end if;
  if VoltageA <>''
    then 
       sqlinsert:=sqlinsert||',VoltageA';
       sqlvalue:=sqlvalue||','||to_char(VoltageA);
      --set sqlupdate=sqlupdate||',VoltageA='||VoltageA
    end if;
  if VoltageB <>''
    then 
       sqlinsert:=sqlinsert||',VoltageB';
       sqlvalue:=sqlvalue||','||to_char(VoltageB);
      --set sqlupdate=sqlupdate||',VoltageB='||VoltageB
    end if;
  if VoltageC <>''
    then 
       sqlinsert:=sqlinsert||',VoltageC';
       sqlvalue:=sqlvalue||','||to_char(VoltageC);
      --set sqlupdate=sqlupdate||',VoltageC='||VoltageC
    end if;
  if CurrentA <>''
    then 
       sqlinsert:=sqlinsert||',CurrentA';
       sqlvalue:=sqlvalue||','||to_char(CurrentA);
      --set sqlupdate=sqlupdate||',CurrentA='||CurrentA
    end if;
  if CurrentB <>''
    then 
       sqlinsert:=sqlinsert||',CurrentB';
       sqlvalue:=sqlvalue||','||to_char(CurrentB);
      --set sqlupdate=sqlupdate||',CurrentB='||CurrentB
    end if;
  if CurrentC <>''
    then 
       sqlinsert:=sqlinsert||',CurrentC';
       sqlvalue:=sqlvalue||','||to_char(CurrentC);
      --set sqlupdate=sqlupdate||',CurrentC='||CurrentC
    end if;
  if Current0 <>''
    then 
       sqlinsert:=sqlinsert||',Current0';
       sqlvalue:=sqlvalue||','||to_char(Current0);
      --set sqlupdate=sqlupdate||',Current0='||Current0
    end if;
  if PowerSZZ <>''
    then 
       sqlinsert:=sqlinsert||',PowerSZZ';
       sqlvalue:=sqlvalue||','||to_char(PowerSZZ);
      --set sqlupdate=sqlupdate||',PowerSZZ='||PowerSZZ
    end if;
  if PowerSZA <>''
    then 
       sqlinsert:=sqlinsert||',PowerSZA';
       sqlvalue:=sqlvalue||','||to_char(PowerSZA);
      --set sqlupdate=sqlupdate||',PowerSZA='||PowerSZA
    end if;
  if PowerSZB <>''
    then 
       sqlinsert:=sqlinsert||',PowerSZB';
       sqlvalue:=sqlvalue||','||to_char(PowerSZB);
      --set sqlupdate=sqlupdate||',PowerSZB='||PowerSZB
    end if;
  if PowerSZC <>''
    then 
       sqlinsert:=sqlinsert||',PowerSZC';
       sqlvalue:=sqlvalue||','||to_char(PowerSZC);
      --set sqlupdate=sqlupdate||',PowerSZC='||PowerSZC
    end if;
   sqlinsert:=sqlinsert||')';
   sqlvalue:=sqlvalue||')';
if Cnt = 0 --不存在数据
    then
      --print(sqlinsert||sqlvalue)
      execute immediate sqlinsert||sqlvalue;
    end if;
  --else
   --   if sqlupdate<>''
     -- begin
       -- set sqlupdate='update AmMeterPDDatas set '+substring(sqlupdate,2,len(sqlupdate)-1)+' where AmMeter_ID='+convert(char(10),meterid)+' and ValueTime = '''+CONVERT(CHAR(19), readtime, 120)+''''
        --print(sqlupdate)
        --exec(sqlupdate)
      --end
       Result:=0;
end; -- end of AutoUpSaveThreePhase









CREATE or replace procedure AutoUpSaveWater
(
gatheraddress varchar,
metertype varchar,
meteraddress varchar,
commandcode varchar,
readtime date,
datavalue varchar,
Result out int  
)
as
 meterid int;
 beilv number(10,2);
 Xiuzheng number(18,2);
 invalue number(18,2);
 Cnt int;				  --该表计已抄数据记录数
 ValueTime date;	  --
 ZValueZY number(18,2); --
begin

	
	 beilv:=1;
	 Xiuzheng:=0;

	-- 默认当水表来处理
	if metertype='2' then
		select WaterMeter_ID into meterid
		from VES_WaterMeter where Gather_Address=gatheraddress and WaterMeter_485Address=meteraddress;

                select nvl(beilv,1) into beilv
		from VES_WaterMeter where Gather_Address=gatheraddress and WaterMeter_485Address=meteraddress;

                select nvl(Xiuzheng,0) into Xiuzheng
		from VES_WaterMeter where Gather_Address=gatheraddress and WaterMeter_485Address=meteraddress;
	-- 其它的时候，返回错误
	else
			 Result:=-1;
			return;
		end if;
	if meterid=0
  then 
       Result:=-1;
      return;
  end if;				
	--只存了当前正向流量
	if commandcode='0C0110' or commandcode='0C0164' or commandcode='0D0264' or commandcode='' or commandcode is null 
		then
			  --declare SplitEqualPos int      --记录等号截取位置     
			  --declare dataname varchar(20)
			  --declare datavalue varchar(20)
 
			  --select SplitEqualPos = CHARINDEX('=', datastr) 
			  --select dataname=LEFT(datastr, SplitEqualPos - 1)   
			  --select datavalue=RIGHT(datastr, Len(datastr)-SplitEqualPos)     

			  --考虑表计的倍率和换表调整数
			 
			   invalue:=to_number(datavalue,'9999999999999999.99')*beilv+Xiuzheng;
			
			  --插入数据的时间要比当前时间小

			  if readtime >sysdate+1/24
				  then
					-- 101 水表errcode 插入数据的时间要比当前时间小 
					insert into AmMeterErrorDatas(AmMeter_ID,ValueTime,ZValueZY,ErrorCode) 
						values(meterid,readtime,to_number(invalue,'9999999999999999.99'),101);  -- 101 水表
					 Result:=101;
					return;
				  end if;
			  
			 
			  
			   ZValueZY:=-1;
			  select ZValueZY into ZValueZY
			  from
				(
				select * from
(select * from WaterMeterDatas where WaterMeter_ID=meterid  and ValueTime < readtime
order by ValueTime desc) where rownum=1 order by rownum asc
				) a;
			  if invalue<ZValueZY
				  then
					insert into AmMeterErrorDatas(AmMeter_ID,ValueTime,ZValueZY,ErrorCode) 
    					values(meterid,readtime,to_number(invalue,'9999999999999999.99'),102);
--	102：插入的数据小于最后一次插入的数据
						 Result:=102;
						return;
				  end if;
				  
			   ZValueZY:=-1;
			  --select Cnt = count(WaterMeter_ID) from WaterMeterDatas where WaterMeter_ID=meterid
			 -- if(Cnt = 0) --不存在数据
				--	begin
				--	  insert into WaterMeterDatas(WaterMeter_ID,ValueTime,ZValueZY) 
					--	  values(meterid,readtime,to_number(invalue,'9999999999999999.99'));
					--  set Result=0
					--end

			  select  count(WaterMeter_ID) into Cnt from WaterMeterDatas where WaterMeter_ID=meterid and ValueTime = readtime;
			  --print Cnt
			  if Cnt > 0 --存在数据
					then
						-- 103 水表errcode 插入数据的时间重复
						insert into AmMeterErrorDatas(AmMeter_ID,ValueTime,ZValueZY,ErrorCode) values(meterid,readtime,to_number(invalue,'9999999999999999.99'),103);  
						 Result:=103;
						return;
					end if;

			  insert into WaterMeterDatas(WaterMeter_ID,ValueTime,ZValueZY) 
							values(meterid,readtime,to_number(invalue,'9999999999999999.99'));	
			   Result:=0;
							
		end if;	-- end of 当前正向流量
end; -- end of AutoUpSaveWater







CREATE or replace procedure BookAdd
(
Book_Num varchar,
Book_Name varchar,
Book_Style int,
Book_Sex int,
Book_Birth date,
Book_Phone varchar,
Book_Priority int default 0,
Result out int  
)
as
begin
	insert into Book(Book_Num,Book_Name,Book_Style,Book_Sex,Book_Birth,Book_Phone,Book_Priority)
	values(Book_Num,Book_Name,Book_Style,Book_Sex,Book_Birth,Book_Phone,Book_Priority);
	select max(Book_ID) into Result from Book;
end;



CREATE or replace procedure BookDel
(
Book_ID int
)
as
begin

delete from Book where Book_ID=Book_ID;
end;


CREATE or replace  procedure BookEdit
(
Book_ID int,
Book_Num varchar,
Book_Name varchar,
Book_Style int,
Book_Sex int,
Book_Birth date,
Book_Phone varchar,
Book_Priority int
)
as
begin

update Book set Book_Num=Book_Num,
Book_Name=Book_Name,
Book_Style=Book_Style,
Book_Sex=Book_Sex,
Book_Birth=Book_Birth,
Book_Phone=Book_Phone,
Book_Priority=Book_Priority
where Book_ID=Book_ID;
end;





CREATE or replace procedure CheckPowerNum
(
Users_ID int ,
Power_Num varchar,
result out int 
)
AS
begin
select count(Users_ID) into result from V_UserToPower where Users_ID=Users_ID and Power_Num=Power_Num;
end;




CREATE or replace procedure CreateT_HourAm
(
selectTable varchar
)
as
 SQL1 varchar(2000);--sql为oracle的关键字不可以作为变量名
begin

 SQL1:='Alter TABLE '||selectTable||' (
	ValueId varchar (15) COLLATE Chinese_PRC_CI_AS NOT NULL ,
	AmMeter_ID int NULL ,
	SelectTime varchar (13) COLLATE Chinese_PRC_CI_AS NULL ,
	ZGross number(8, 2) NULL ,
	JGross number(8, 2) NULL ,
	FGross number(8, 2) NULL ,
	PGross number(8, 2) NULL ,
	GGross number(8, 2) NULL ,
	AZGross number(8, 2) NULL ,
	BZGross number(8, 2) NULL ,
	CZGross number(8, 2) NULL ,
	insertTime date NULL 
) ';

execute immediate SQL1;
 SQL1:='ALTER TABLE '||selectTable||' WITH NOCHECK ADD 
	CONSTRAINT PK_'||selectTable||' PRIMARY KEY  CLUSTERED 
	(
		ValueId
	) ';
execute immediate SQL1;
 SQL1:='ALTER TABLE '||selectTable||' ADD 
	CONSTRAINT DF_'||selectTable||'_insertTime DEFAULT (sysdate) FOR insertTime';
execute immediate SQL1;

--编辑视图
 SQL1:='alter View SelectTheT_HourAm;
as 
select * from '||selectTable;
execute immediate SQL1;

end;







CREATE or replace procedure CreateT_HourWater
(
selectTable varchar
)
as
 SQL1 varchar(2000);--由于sql为oracle的关键字不可以作为变量名
begin

 SQL1:='Alter TABLE '||selectTable||' (
	ValueId varchar (15) COLLATE Chinese_PRC_CI_AS NOT NULL ,
	WaterMeter_ID int NULL ,
	SelectTime varchar (13) COLLATE Chinese_PRC_CI_AS NULL ,
	ZGross number(8, 2) NULL ,
	SGross number(8, 2) NULL ,
	insertTime date NULL 
) ';

execute immediate SQL1;
 SQL1:='ALTER TABLE '||selectTable||' WITH NOCHECK ADD 
	CONSTRAINT PK_'||selectTable||' PRIMARY KEY  CLUSTERED 
	(
		ValueId
	) ';
execute immediate SQL1;
 SQL1:='ALTER TABLE '||selectTable||' ADD 
	CONSTRAINT DF_'||selectTable||'_insertTime DEFAULT (sysdate) FOR insertTime';
execute immediate SQL1;

--编辑视图
 SQL1:='alter View SelectTheT_HourWater
as 
select * from '||selectTable;
execute immediate SQL1;

end;


CREATE or replace procedure D_AmAbnormalBoDong
(
SelectWhere varchar default '',--搜索的条件
TimeList varchar default '',--搜索的条件
strGetFields varchar default '*', 
doCount number default 0,--返回记录总数, 非 0 值则返回
orderFldDesc  varchar default '',--排序字段及排序方向，如addDate desc,id desc（排序字段需通过selectOrderFldName指定，可为空） 
PageSize     int default 10,           -- 页尺寸 ,如果为 0 不执行分页功能
PageIndex    int default 1,            -- 页码 
strWhere     varchar default  ''  -- 查询条件(注意: 不要加where) 
)
as
 sql1 varchar(6000);--sql=》sql1
 SelectWhere1 varchar(100);
 TimeList1 varchar(200);
 strWhere1 varchar(1000);
 keyFldName      varchar(80);  -- 主键字段 
begin
 sql1:='';
 SelectWhere1:=SelectWhere;
 TimeList1 :=TimeList;
 strWhere1 :=strWhere;
if not(SelectWhere1 is null or rtrim(SelectWhere1) = '' ) then
 SelectWhere1:=' where '||SelectWhere1;
end if;

if not(TimeList1 is null or rtrim(TimeList1) = '' ) then
 TimeList1:=' where '||TimeList1;
end if;

if doCount=0 
then
	if not(strWhere1 is null or rtrim(strWhere1) = '' ) then
	 strWhere1:=' where '||strWhere1;

	 sql1:='select count(AmMeter_ID) from (
		select A.AmMeter_ID,convert(char(10),LastTime,120) as LastTime,convert(char(10),ThisTime,120) as ThisTime,LastGross,ThisGross,ABS(GrossCha) GrossCha ,cast((GrossCha/LastGross)*100 as numeric(8,2)) GrossPercent,InsertTime,(select Architecture_Name from Architecture as c where c.Architecture_ID=b.Architecture_ID)Architecture_Name,(select Gather_Name from Gather as c where c.Gather_ID=b.Gather_ID)Gather_Name,AmMeter_Name,AmMeter_485Address,ConsumerNum,ConsumerName from 
		(select AmMeter_ID,LastTime,ThisTime,LastGross,ThisGross,(ThisGross-LastGross)GrossCha,InsertTime from AmAbnormalBoDong '||TimeList1||')A,
		(select AmMeter_ID,Architecture_ID,Gather_ID,AmMeter_Name,AmMeter_485Address,ConsumerNum,ConsumerName from AmMeter '||SelectWhere1||')B 
		where A.AmMeter_ID=B.AmMeter_ID
		)T '||strWhere1;

	execute immediate sql1;
     end if;
else

	if not(strWhere1 is null or rtrim(strWhere1) = '' ) then
	 strWhere1:=' where '||strWhere1;
	
	 sql1:='select '||strGetFields||' from (
			select A.AmMeter_ID,convert(char(10),LastTime,120) as LastTime,convert(char(10),ThisTime,120) as ThisTime,LastGross,ThisGross,ABS(GrossCha)GrossCha,cast((GrossCha/LastGross)*100 as numeric(8,2))GrossPercent,InsertTime,(select Architecture_Name from Architecture as c where c.Architecture_ID=b.Architecture_ID)Architecture_Name,(select Gather_Name from Gather as c where c.Gather_ID=b.Gather_ID)Gather_Name,AmMeter_Name,AmMeter_485Address,ConsumerNum,ConsumerName from 
		(select AmMeter_ID,LastTime,ThisTime,LastGross,ThisGross,(ThisGross-LastGross)GrossCha,InsertTime from AmAbnormalBoDong '||TimeList1||')A,
		(select AmMeter_ID,Architecture_ID,Gather_ID,AmMeter_Name,AmMeter_485Address,ConsumerNum,ConsumerName from AmMeter '||SelectWhere1||')B 
		where A.AmMeter_ID=B.AmMeter_ID
		)T '||strWhere1;
            end if;

	if PageSize<>0
	then
		
		keyFldName:='AmMeter_ID';  
	/*	exec SearchGetDataByPage sql1,orderFldDesc,keyFldName,PageSize,PageIndex; */
	
	else
	
		execute immediate sql1;
	end if;
end if;
end;









CREATE or replace procedure D_AmAbnormalDaozuo
(
SelectWhere varchar default '',--搜索的条件
TimeList varchar default '',--搜索的条件
strGetFields varchar default '*', 
doCount number default  0,--返回记录总数, 非 0 值则返回
orderFldDesc  varchar default '',--排序字段及排序方向，如addDate desc,id desc（排序字段需通过selectOrderFldName指定，可为空） 
PageSize     int default  10,           -- 页尺寸 ,如果为 0 不执行分页功能
PageIndex    int default  1,            -- 页码 
strWhere     varchar default  ''  -- 查询条件(注意: 不要加where) 
)
as
  sql1 varchar(6000);--sql=》sql1
 SelectWhere1 varchar(100);
 TimeList1 varchar(200);
 strWhere1 varchar(1000);
 keyFldName varchar(80);  -- 主键字段 
begin
 sql1:='';
 SelectWhere1:=SelectWhere;
 TimeList1 :=TimeList;
 strWhere1 :=strWhere;
if not(SelectWhere1 is null or rtrim(SelectWhere1) = '' ) then 
 SelectWhere1:=' where '||SelectWhere1;
end if;

if not(TimeList1 is null or rtrim(TimeList1) = '' ) then
 TimeList1:=' where '||TimeList1;
end if;

if doCount=0 
then
	if not(strWhere1 is null or rtrim(strWhere1) = '' ) then
	 strWhere1:=' where '||strWhere1;

	 sql1:='select count(AmMeter_ID) from (
		select A.AmMeter_ID,convert(char(10),LastTime,120) as LastTime,convert(char(10),ThisTime,120) as ThisTime,LastZValueZY,ThisZValueZY,InsertTime,(select Architecture_Name from Architecture as c where c.Architecture_ID=b.Architecture_ID)Architecture_Name,(select Gather_Name from Gather as c where c.Gather_ID=b.Gather_ID)Gather_Name,AmMeter_Name,AmMeter_485Address,ConsumerNum,ConsumerName from 
		(select AmMeter_ID,LastTime,ThisTime,LastZValueZY,ThisZValueZY,InsertTime from AmAbnormalDaozuo '||TimeList1||')A,
		(select AmMeter_ID,Architecture_ID,Gather_ID,AmMeter_Name,AmMeter_485Address,ConsumerNum,ConsumerName from AmMeter '||SelectWhere1||')B 
		where A.AmMeter_ID=B.AmMeter_ID
		)T '||strWhere1;

	execute immediate sql1;
         end if;
else

	if not(strWhere1 is null or rtrim(strWhere1) = '' ) then
	 strWhere1:=' where '||strWhere1;
	
	 sql1:='select '||strGetFields||' from (
			select A.AmMeter_ID,convert(char(10),LastTime,120) as LastTime,convert(char(10),ThisTime,120) as ThisTime,LastZValueZY,ThisZValueZY,InsertTime,(select Architecture_Name from Architecture as c where c.Architecture_ID=b.Architecture_ID)Architecture_Name,(select Gather_Name from Gather as c where c.Gather_ID=b.Gather_ID)Gather_Name,AmMeter_Name,AmMeter_485Address,ConsumerNum,ConsumerName from 
		(select AmMeter_ID,LastTime,ThisTime,LastZValueZY,ThisZValueZY,InsertTime from AmAbnormalDaozuo '||TimeList1||')A,
		(select AmMeter_ID,Architecture_ID,Gather_ID,AmMeter_Name,AmMeter_485Address,ConsumerNum,ConsumerName from AmMeter '||SelectWhere1||')B 
		where A.AmMeter_ID=B.AmMeter_ID
		)T '||strWhere1;
             end if;

	if PageSize<>0
	then
		
		 keyFldName:='AmMeter_ID';
	/*	exec SearchGetDataByPage sql1,orderFldDesc,keyFldName,PageSize,PageIndex  */
	
	else
	
		execute immediate sql1;
	end if;
end if;
end;









CREATE or replace procedure D_AmAbnormalMiss
(
SelectWhere varchar default '',--搜索的条件
TimeList varchar default '',--搜索的条件
strGetFields varchar default  '*', 
doCount number default  0,--返回记录总数, 非 0 值则返回
orderFldDesc  varchar default '',--排序字段及排序方向，如addDate desc,id desc（排序字段需通过selectOrderFldName指定，可为空） 
PageSize     int default  10,           -- 页尺寸 ,如果为 0 不执行分页功能
PageIndex    int default  1,            -- 页码 
strWhere     varchar default  ''  -- 查询条件(注意: 不要加where) 
)
as
 sql1 varchar(6000);--sql=>sql1
 SelectWhere1 varchar(100);
 TimeList1 varchar(200);
 strWhere1 varchar(1000);
 keyFldName      varchar(80);  -- 主键字段 
begin
 sql1:='';
 SelectWhere1:=SelectWhere;
 TimeList1 :=TimeList;
 strWhere1 :=strWhere;
if not(SelectWhere1 is null or rtrim(SelectWhere1) = '' ) then
 SelectWhere1:=' where '||SelectWhere1;
end if;

if not(TimeList1 is null or rtrim(TimeList1) = '' ) then
 TimeList1:=' where '||TimeList1;
end if;

if doCount=0 
then
	if not(strWhere1 is null or rtrim(strWhere1) = '' ) then
	 strWhere1:=' where '||strWhere1;

	 sql1:='select count(AmMeter_ID) from (
		select A.AmMeter_ID,convert(char(10),ValueTime,120) as ValueTime,Again,InsertTime,(select Architecture_Name from Architecture as c where c.Architecture_ID=b.Architecture_ID)Architecture_Name,(select Gather_Name from Gather as c where c.Gather_ID=b.Gather_ID)Gather_Name,AmMeter_Name,AmMeter_485Address,ConsumerNum,ConsumerName from 
		(select AmMeter_ID,ValueTime,Again,InsertTime from AmAbnormalMiss '||TimeList1||')A,
		(select AmMeter_ID,Architecture_ID,Gather_ID,AmMeter_Name,AmMeter_485Address,ConsumerNum,ConsumerName from AmMeter '||SelectWhere1||')B 
		where A.AmMeter_ID=B.AmMeter_ID
		)T '||strWhere1;

	execute immediate sql1;
     end if;
else

	if not(strWhere1 is null or rtrim(strWhere1) = '' ) then
	 strWhere1:=' where '||strWhere1;
	
	 sql1:='select '||strGetFields||' from (
			select A.AmMeter_ID,convert(char(10),ValueTime,120) as ValueTime,Again,InsertTime,(select Architecture_Name from Architecture as c where c.Architecture_ID=b.Architecture_ID)Architecture_Name,(select Gather_Name from Gather as c where c.Gather_ID=b.Gather_ID)Gather_Name,AmMeter_Name,AmMeter_485Address,ConsumerNum,ConsumerName from 
		(select AmMeter_ID,ValueTime,Again,InsertTime from AmAbnormalMiss '||TimeList1||')A,
		(select AmMeter_ID,Architecture_ID,Gather_ID,AmMeter_Name,AmMeter_485Address,ConsumerNum,ConsumerName from AmMeter '||SelectWhere1||')B 
		where A.AmMeter_ID=B.AmMeter_ID
		)T '||strWhere1;
          end if;

	if PageSize<>0
	then
		
		 keyFldName:='AmMeter_ID';
		/* exec SearchGetDataByPage sql1,orderFldDesc,keyFldName,PageSize,PageIndex */
	else
	
		execute immediate sql1;
	end if;
end if;
end;










CREATE or replace procedure D_AmAbnormalZroe
(
SelectWhere varchar default '',--搜索的条件
TimeList varchar default '',--搜索的条件
strGetFields varchar default '*', 
doCount number default 0,--返回记录总数, 非 0 值则返回
orderFldDesc  varchar default '',--排序字段及排序方向，如addDate desc,id desc（排序字段需通过selectOrderFldName指定，可为空） 
PageSize     int default  10,           -- 页尺寸 ,如果为 0 不执行分页功能
PageIndex    int default  1,            -- 页码 
strWhere     varchar default ''  -- 查询条件(注意: 不要加where) 
)
as
 sql1 varchar(6000);--sql=》sql1
 SelectWhere1 varchar(100);
 TimeList1 varchar(200);
 strWhere1 varchar(1000);
 keyFldName      varchar(80);  -- 主键字段 
begin
 sql1:='';
 SelectWhere1:=SelectWhere;
 TimeList1 :=TimeList;
 strWhere1 :=strWhere;

if not(SelectWhere1 is null or rtrim(SelectWhere1) = '' ) then
 SelectWhere1:=' where '||SelectWhere1;
end if;

if not(TimeList1 is null or rtrim(TimeList1) = '' ) then
 TimeList1:=' where '||TimeList1;
end if;

if doCount=0 
then
	if not(strWhere1 is null or rtrim(strWhere1) = '' ) then
	 strWhere1:=' where '||strWhere1;

	 sql1:='select count(AmMeter_ID) from (
		select A.AmMeter_ID,convert(char(10),ValueTime,120) as ValueTime,Again,InsertTime,(select Architecture_Name from Architecture as c where c.Architecture_ID=b.Architecture_ID)Architecture_Name,(select Gather_Name from Gather as c where c.Gather_ID=b.Gather_ID)Gather_Name,AmMeter_Name,AmMeter_485Address,ConsumerNum,ConsumerName from 
		(select AmMeter_ID,ValueTime,Again,InsertTime from AmAbnormalZroe '||TimeList1||')A,
		(select AmMeter_ID,Architecture_ID,Gather_ID,AmMeter_Name,AmMeter_485Address,ConsumerNum,ConsumerName from AmMeter '||SelectWhere1||')B 
		where A.AmMeter_ID=B.AmMeter_ID
		)T '||strWhere1;

	execute immediate sql1;
            end if;
else

	if not(strWhere1 is null or rtrim(strWhere1) = '' ) then
	 strWhere1:=' where '||strWhere1;
	
	 sql1:='select '||strGetFields||' from (
			select A.AmMeter_ID,convert(char(10),ValueTime,120) as ValueTime,Again,InsertTime,(select Architecture_Name from Architecture as c where c.Architecture_ID=b.Architecture_ID)Architecture_Name,(select Gather_Name from Gather as c where c.Gather_ID=b.Gather_ID)Gather_Name,AmMeter_Name,AmMeter_485Address,ConsumerNum,ConsumerName from 
		(select AmMeter_ID,ValueTime,Again,InsertTime from AmAbnormalZroe '||TimeList1||')A,
		(select AmMeter_ID,Architecture_ID,Gather_ID,AmMeter_Name,AmMeter_485Address,ConsumerNum,ConsumerName from AmMeter '||SelectWhere1||')B 
		where A.AmMeter_ID=B.AmMeter_ID
		)T '||strWhere1;
           end if;

	if PageSize<>0
	then
		 keyFldName:='AmMeter_ID';
		/* exec SearchGetDataByPage sql1,orderFldDesc,keyFldName,PageSize,PageIndex */
	else
	
		execute immediate sql1;
	end if;
end if;
end;






CREATE or replace procedure  D_AmSupply
(
SelectWhere varchar default '',--搜索的条件
CryStyle varchar default 'All',--报表统计类型
strGetFields varchar default '*', 
doCount number default 0,--返回记录总数, 非 0 值则返回
orderFldDesc  varchar default '',--排序字段及排序方向，如addDate desc,id desc（排序字段需通过selectOrderFldName指定，可为空） 
PageSize     int default  10,           -- 页尺寸 ,如果为 0 不执行分页功能
PageIndex    int default  1,            -- 页码 
strWhere     varchar default  ''  -- 查询条件(注意: 不要加where) 
)
as
 sql1 varchar(6000);--sql=》sql1
 SelectWhere1 varchar(100);
 strWhere1 varchar(1000);
 keyFldName      varchar(80);  -- 主键字段 
begin
 sql1:='';
SelectWhere1:=SelectWhere;
 strWhere1 :=strWhere;
if not(SelectWhere1 is null or rtrim(SelectWhere1) = '' ) then
 SelectWhere1:=' where '||SelectWhere1;
end if;

if doCount=0 
then
	if not(strWhere1 is null or rtrim(strWhere1) = '' ) then
	 strWhere1:=' where '||strWhere1;
         end if;

	if CryStyle='All'--全部数据
	then	
	 sql1:='select count(AmMeter_ID) from (
	select a.AmMeter_ID,AmMeter_Name,AmMeter_485Address as AmMeter_Address,ConsumerNum,ConsumerName,(select Gather_Name from Gather as c where c.Gather_ID=b.Gather_ID)Gather_Name,(select Architecture_Name from Architecture as c where c.Architecture_ID=b.Architecture_ID)Architecture_Name,ValueTime,(case SupplyState when ''1'' then ''开闸'' else ''合闸'' end)SupplyState,(case Operate when ''1'' then ''成功'' else ''失败'' end)Operate,(case Style when ''1'' then ''手动'' else ''系统'' end)Style from (select AmMeter_ID,ValueTime,SupplyState,Operate,Style from AmMeterSupply '||SelectWhere1||')a,AmMeter as b where a.AmMeter_ID=b.AmMeter_ID
	)T '||strWhere1;
	
	elsif  CryStyle='Fail'--失败数据
	then
	
	 sql1:='select count(AmMeter_ID) from (
	select a.AmMeter_ID,AmMeter_Name,AmMeter_485Address as AmMeter_Address,ConsumerNum,ConsumerName,(select Gather_Name from Gather as c where c.Gather_ID=b.Gather_ID)Gather_Name,(select Architecture_Name from Architecture as c where c.Architecture_ID=b.Architecture_ID)Architecture_Name,ValueTime,(case SupplyState when ''1'' then ''开闸'' else ''合闸'' end)SupplyState,(case Operate when ''1'' then ''成功'' else ''失败'' end)Operate,(case Style when ''1'' then ''手动'' else ''系统'' end)Style from (select AmMeter_ID,ValueTime,SupplyState,Operate,Style from AmMeterSupply '||SelectWhere1||' and MeterState_ID=''0'')a,AmMeter as b where a.AmMeter_ID=b.AmMeter_ID
	)T '||strWhere1;
	end if;
	execute immediate sql1;
else
	if not(strWhere1 is null or rtrim(strWhere1) = '' ) then
	 strWhere1:=' where '||strWhere1;
         end if;

	if CryStyle='All'--全部数据
	then
	
	 sql1:='select '||strGetFields||' from (
	select a.AmMeter_ID,AmMeter_Name,AmMeter_485Address as AmMeter_Address,ConsumerNum,ConsumerName,(select Gather_Name from Gather as c where c.Gather_ID=b.Gather_ID)Gather_Name,(select Architecture_Name from Architecture as c where c.Architecture_ID=b.Architecture_ID)Architecture_Name,ValueTime,(case SupplyState when ''1'' then ''开闸'' else ''合闸'' end)SupplyState,(case Operate when ''1'' then ''成功'' else ''失败'' end)Operate,(case Style when ''1'' then ''手动'' else ''系统'' end)Style from (select AmMeter_ID,ValueTime,SupplyState,Operate,Style from AmMeterSupply '||SelectWhere1||')a,AmMeter as b where a.AmMeter_ID=b.AmMeter_ID
	)T '||strWhere1;
	
	elsif CryStyle='Fail'--失败数据
	then	
	 sql1:='select '||strGetFields||' from (
	select a.AmMeter_ID,AmMeter_Name,AmMeter_485Address as AmMeter_Address,ConsumerNum,ConsumerName,(select Gather_Name from Gather as c where c.Gather_ID=b.Gather_ID)Gather_Name,(select Architecture_Name from Architecture as c where c.Architecture_ID=b.Architecture_ID)Architecture_Name,(case SupplyState when ''1'' then ''开闸'' else ''合闸'' end)SupplyState,ZValueZY,(case Operate when ''1'' then ''成功'' else ''失败'' end)Operate,(case Style when ''1'' then ''手动'' else ''系统'' end)Style from (select AmMeter_ID,ValueTime,SupplyState,Operate,Style from AmMeterSupply '||SelectWhere1||' and MeterState_ID=''0'')a,AmMeter as b where a.AmMeter_ID=b.AmMeter_ID
	)T '||strWhere1;
	end if;

	if PageSize<>0
	then
	    keyFldName:='AmMeter_ID';
	/*	exec SearchGetDataByPage sql1,orderFldDesc,keyFldName,PageSize,PageIndex */
	
	else
	
		execute immediate sql1;
	end if;
end if;
end;









CREATE or replace procedure D_ArchitectureChainTime
(
SelectWhere varchar default '',--搜索的条件
Architecture_Style varchar default '0',
Energy_Style varchar default '',
Energy_Style1 varchar default '',
TimeList varchar default '',--搜索的条件
TimeList2 varchar default '',--搜索的条件
CryStyle varchar default 'Month',--报表统计类型
strGetFields varchar default  '*', 
doCount number default  0,--返回记录总数, 非 0 值则返回
orderFldDesc  varchar default '',--排序字段及排序方向，如addDate desc,id desc（排序字段需通过selectOrderFldName指定，可为空） 
PageSize     int default  10,           -- 页尺寸 ,如果为 0 不执行分页功能
PageIndex    int default  1,            -- 页码 
strWhere     varchar default ''  -- 查询条件(注意: 不要加where) 
)
as
 sql1 varchar(6000);
 SelectWhere1 varchar(100);
 TimeList1 varchar(200);
 strWhere1 varchar(1000);
 keyFldName      varchar(80);  -- 主键字段 
 Astyle varchar(50);
TimeList2r varchar(200);--代替TimeList2
 where2 varchar(200);
 EAstyle varchar(200);
 b1 varchar(20);
 b2 varchar(20);
 
begin
 SelectWhere1:=SelectWhere;
 TimeList1 :=TimeList;
 strWhere1 :=strWhere;
 sql1:='';
TimeList2r:=TimeList2;

if not(SelectWhere1 is null or rtrim(SelectWhere1) = '' ) then
 SelectWhere1:=' and '||SelectWhere1;
end if;


if Architecture_Style is null or rtrim(Architecture_Style) = ''or Architecture_Style='0'  then
 Astyle:='';
else
 Astyle:=' and Architecture_Style='''||Architecture_Style||'''';
end if;

if not(TimeList1 is null or rtrim(TimeList1) = '' ) then
 TimeList1:=' and '||TimeList1;
end if;

if not(TimeList2r is null or rtrim(TimeList2r) = '' ) then
 TimeList2r:=' and '||TimeList2r;
end if;



if Energy_Style is null or rtrim(Energy_Style) = ''or Energy_Style='0' 
then
 EAstyle:='''全部能耗'' as Energy_Subentry,''0'' Energy_ID,''全部能耗'' as Energy_Subentry1,''0'' Energy_ID1,';
 where2:='';

else


 select DictionaryValue_Value into b1 from DictionaryValue where Dictionary_ID=7 and DictionaryValue_Num=Energy_Style;
	if Energy_Style1 is null or rtrim(Energy_Style1) = ''or Energy_Style1='0' 
              then	
		 EAstyle:=''''||b1||''' as Energy_Subentry,'''||Energy_Style||''' Energy_ID,''全部能耗'' as Energy_Subentry1,''0'' Energy_ID1,';
                 where2:=' and substring(AmMeter_Num,14,1)='''||Energy_Style||'''';
              
	else	         
        select DictionaryValue_Value into b2 from DictionaryValue where Dictionary_ID=8 and DictionaryValue_Num=Energy_Style1;
            EAstyle:=''''||b1||''' as Energy_Subentry,'''||Energy_Style||''' Energy_ID,'''||b2||''' as Energy_Subentry1,'''||Energy_Style1||''' Energy_ID1,';
            where2:=' and substring(AmMeter_Num,14,1)='''||Energy_Style||''' and substring(AmMeter_Num,14,2)='''||Energy_Style1||'''' ;
	end if;

end if;

if doCount=0 
then
	if not(strWhere1 is null or rtrim(strWhere1) = '' ) then
	 strWhere1:=' where '||strWhere1;
         end if;
	
	if CryStyle='Month'     --月报表 
	then
	
	 sql1:='select count(Architecture_ID) from (
select  '||EAstyle||' a.Architecture_ID,
(select DictionaryValue_Value  from DictionaryValue as c where Architecture_Style=c.DictionaryValue_Num and Dictionary_ID=3)Architecture_Stylename, 
(select Area_Name from Area c where a.Area_ID=c.Area_ID)Area_Name,
Architecture_Num,Architecture_Name,BZGross,SZGross,CGross,BFGross 
from Architecture as a,
(select T1.Architecture_ID,BZGross,SZGross,(BZGross-SZGross)CGross,cast(cast((BZGross-SZGross)/SZGross*100 as numeric(12,3)) as varchar(20))||''%''  BFGross from 
(select Architecture_ID,sum(ZGross) BZGross from (select Architecture_ID,ZGross from T_MonthAm as a,AmMeter as b where a.AmMeter_ID=b.AmMeter_ID '||SelectWhere1||TimeList1||where2||')T group by Architecture_ID)as T1 left join 
(select Architecture_ID,sum(ZGross) SZGross from (select Architecture_ID,ZGross from T_MonthAm as a,AmMeter as b where a.AmMeter_ID=b.AmMeter_ID '||SelectWhere1||TimeList2r||where2||')T group by Architecture_ID)as T2  on T1.Architecture_ID=T2.Architecture_ID)b
where a.Architecture_ID=b.Architecture_ID)T'||strWhere1;

	elsif CryStyle='Year'--年报表
	then	
	 sql1:='select count(Architecture_ID) from (
select  '||EAstyle||' a.Architecture_ID,
(select DictionaryValue_Value  from DictionaryValue as c where Architecture_Style=c.DictionaryValue_Num and Dictionary_ID=3)Architecture_Stylename, 
(select Area_Name from Area c where a.Area_ID=c.Area_ID)Area_Name,
Architecture_Num,Architecture_Name,BZGross,SZGross,CGross,BFGross 
from Architecture as a,
(select T1.Architecture_ID,BZGross,SZGross,(BZGross-SZGross)CGross,cast(cast((BZGross-SZGross)/SZGross*100 as numeric(12,3)) as varchar(20))||''%''  BFGross from 
(select Architecture_ID,sum(ZGross) BZGross from (select Architecture_ID,ZGross from T_MonthAm as a,AmMeter as b where a.AmMeter_ID=b.AmMeter_ID '||SelectWhere1||TimeList1||where2||')T group by Architecture_ID)as T1 left join 
(select Architecture_ID,sum(ZGross) SZGross from (select Architecture_ID,ZGross from T_MonthAm as a,AmMeter as b where a.AmMeter_ID=b.AmMeter_ID '||SelectWhere1||TimeList2r||where2||')T group by Architecture_ID)as T2  on T1.Architecture_ID=T2.Architecture_ID)b
where a.Architecture_ID=b.Architecture_ID)T'||strWhere1;
	
	
	end if;
	--print(sql1)
	execute immediate sql1;

else

	if not(strWhere1 is null or rtrim(strWhere1) = '' ) then
	 strWhere1:=' where '||strWhere1;
         end if;
	
	if CryStyle='Month'--月报表
	then	
	 sql1:='select '||strGetFields||' from (
select  '||EAstyle||' a.Architecture_ID,
(select DictionaryValue_Value  from DictionaryValue as c where Architecture_Style=c.DictionaryValue_Num and Dictionary_ID=3)Architecture_Stylename, 
(select Area_Name from Area c where a.Area_ID=c.Area_ID)Area_Name,
Architecture_Num,Architecture_Name,BZGross,SZGross,CGross,BFGross 
from Architecture as a,
(select T1.Architecture_ID,BZGross,SZGross,(BZGross-SZGross)CGross,cast(cast((BZGross-SZGross)/SZGross*100 as numeric(12,3)) as varchar(20))||''%''  BFGross from 
(select Architecture_ID,sum(ZGross) BZGross from (select Architecture_ID,ZGross from T_MonthAm as a,AmMeter as b where a.AmMeter_ID=b.AmMeter_ID '||SelectWhere1||TimeList1||where2||')T group by Architecture_ID)as T1 left join 
(select Architecture_ID,sum(ZGross) SZGross from (select Architecture_ID,ZGross from T_MonthAm as a,AmMeter as b where a.AmMeter_ID=b.AmMeter_ID '||SelectWhere1||TimeList2r||where2||')T group by Architecture_ID)as T2  on T1.Architecture_ID=T2.Architecture_ID)b
where a.Architecture_ID=b.Architecture_ID)T'||strWhere1;
	
	elsif CryStyle='Year'--年报表
	then
	 sql1:='select '||strGetFields||' from (
select  '||EAstyle||' a.Architecture_ID,
(select DictionaryValue_Value  from DictionaryValue as c where Architecture_Style=c.DictionaryValue_Num and Dictionary_ID=3)Architecture_Stylename, 
(select Area_Name from Area c where a.Area_ID=c.Area_ID)Area_Name,
Architecture_Num,Architecture_Name,BZGross,SZGross,CGross,BFGross 
from Architecture as a,
(select T1.Architecture_ID,BZGross,SZGross,(BZGross-SZGross)CGross,cast(cast((BZGross-SZGross)/SZGross*100 as numeric(12,3)) as varchar(20))||''%''  BFGross from 
(select Architecture_ID,sum(ZGross) BZGross from (select Architecture_ID,ZGross from T_MonthAm as a,AmMeter as b where a.AmMeter_ID=b.AmMeter_ID '||SelectWhere1||TimeList1||where2||')T group by Architecture_ID)as T1 left join 
(select Architecture_ID,sum(ZGross) SZGross from (select Architecture_ID,ZGross from T_MonthAm as a,AmMeter as b where a.AmMeter_ID=b.AmMeter_ID '||SelectWhere1||TimeList2r||where2||')T group by Architecture_ID)as T2  on T1.Architecture_ID=T2.Architecture_ID)b
where a.Architecture_ID=b.Architecture_ID)T'||strWhere1;
	
	
	end if;

	if PageSize<>0
	then		
		 keyFldName:='Architecture_ID' ; 
	/*	exec SearchGetDataByPage sql1,orderFldDesc,keyFldName,PageSize,PageIndex   */
	else	
		execute immediate sql1;
	end if;
end if;
end;









CREATE or replace procedure D_ArchitectureSameTime
(
SelectWhere varchar default '',--搜索的条件
Architecture_Style varchar default '0',
Energy_Style varchar default '',
Energy_Style1 varchar default '',
TimeList varchar default '',--搜索的条件
CryStyle varchar default 'Month',--报表统计类型
strGetFields varchar default '*', 
doCount number default 0,--返回记录总数, 非 0 值则返回
orderFldDesc  varchar default '',--排序字段及排序方向，如addDate desc,id desc（排序字段需通过selectOrderFldName指定，可为空） 
PageSize     int default 10,           -- 页尺寸 ,如果为 0 不执行分页功能
PageIndex    int default 1,            -- 页码 
strWhere     varchar default ''  -- 查询条件(注意: 不要加where) 
)
as
 sql1 varchar(6000);--sql=>sql1
 SelectWhere1 varchar(100);
 TimeList1 varchar(200);
 strWhere1 varchar(1000);
 keyFldName      varchar(80);  -- 主键字段 
 Astyle varchar(50);
 where2 varchar(200);
 EAstyle varchar(200);
 b1 varchar(20);
 b2 varchar(20);
begin
 sql1:='';
 SelectWhere1:=SelectWhere;
 TimeList1 :=TimeList;
 strWhere1 :=strWhere;
if not(SelectWhere1 is null or rtrim(SelectWhere1) = '' ) then
 SelectWhere1:=' where '||SelectWhere1;
 end if;


if Architecture_Style is null or rtrim(Architecture_Style) = ''or Architecture_Style='0'  then
 Astyle:='';
else
  Astyle:=' and Architecture_Style='''||Architecture_Style||'''';
end if;
if not(TimeList1 is null or rtrim(TimeList1) = '' ) then
 TimeList1:=' where '||TimeList1;
end if;



if Energy_Style is null or rtrim(Energy_Style) = ''or Energy_Style='0' 
then
 EAstyle:='''全部能耗'' as Energy_Subentry,''0'' Energy_ID,''全部能耗'' as Energy_Subentry1,''0'' Energy_ID1,';
 where2:='';
else


select DictionaryValue_Value into b1 from DictionaryValue where Dictionary_ID=7 and DictionaryValue_Num=Energy_Style;
	if Energy_Style1 is null or rtrim(Energy_Style1) = ''or Energy_Style1='0' 
              then	
		 EAstyle:=''''||b1||''' as Energy_Subentry,'''||Energy_Style||''' Energy_ID,''全部能耗'' as Energy_Subentry1,''0'' Energy_ID1,';
                where2:=' and substring(AmMeter_Num,14,1)='''||Energy_Style||'''';
              
	else        
           select DictionaryValue_Value into b2 from DictionaryValue where Dictionary_ID=8 and DictionaryValue_Num=Energy_Style1;
            EAstyle:=''''||b1||''' as Energy_Subentry,'''||Energy_Style||''' Energy_ID,'''||b2||''' as Energy_Subentry1,'''||Energy_Style1||''' Energy_ID1,';
            where2:=' and substring(AmMeter_Num,14,1)='''||Energy_Style||''' and substring(AmMeter_Num,14,2)='''||Energy_Style1||'''' ;
	end if;

end if;

if doCount=0 
then
	if not(strWhere1 is null or rtrim(strWhere1) = '' ) then
	 strWhere1:=' where '||strWhere1;
         end if;
	
	if CryStyle='Month'--月报表
	then	
	 sql1:='select count(Architecture_ID) from (
select '||EAstyle||'((select DictionaryValue_Value  from DictionaryValue as b where T4.Architecture_Style=b.DictionaryValue_Num and Dictionary_ID=3)  )Architecture_Stylename,T4.Architecture_ID,T5.Architecture_Name,T5.Architecture_Num,Area_Name,ZGross,SelectTime from Architecture T4 inner join
(select Architecture_ID,(select Architecture_Name from Architecture as a where T2.Architecture_ID=a.Architecture_ID)Architecture_Name,(select Architecture_Num from Architecture a where T2.Architecture_ID=a.Architecture_ID)Architecture_Num,(select Area_Name from Area b where T2.Area_ID=b.Area_ID)Area_Name,sum(ZGross)ZGross,SelectTime from AmMeter T2 inner join 
(select AmMeter_ID,ZGross,SelectTime from T_MonthAm '||TimeList1||')as T1 on T1.AmMeter_ID=T2.AmMeter_ID  '||SelectWhere1||where2||' group by Architecture_ID,SelectTime,Area_ID)T5 on T4.Architecture_ID=T5.Architecture_ID '||Astyle||'
) T8 '||strWhere1;


	
	elsif CryStyle='Year'--年报表
	then
	 sql1:='select count(Architecture_ID) from (
select '||EAstyle||'((select DictionaryValue_Value  from DictionaryValue as b where T4.Architecture_Style=b.DictionaryValue_Num and Dictionary_ID=3)  )Architecture_Stylename,T4.Architecture_ID,T5.Architecture_Name,T5.Architecture_Num,Area_Name,ZGross,SelectTime from Architecture T4 inner join
(select Architecture_ID,(select Architecture_Name from Architecture as a where T2.Architecture_ID=a.Architecture_ID)Architecture_Name,(select Architecture_Num from Architecture a where T2.Architecture_ID=a.Architecture_ID)Architecture_Num,(select Area_Name from Area b where T2.Area_ID=b.Area_ID)Area_Name,sum(ZGross)ZGross,TYear as SelectTime from AmMeter T2 inner join  
(select AmMeter_ID,sum(ZGross) ZGross,TYear from (select AmMeter_ID,ZGross,substring(SelectTime,1,4) TYear from T_MonthAm)T1  group by AmMeter_ID,TYear)as T1 on T1.AmMeter_ID=T2.AmMeter_ID '||SelectWhere1||where2||' group by Architecture_ID,TYear,Area_ID)T5 on T4.Architecture_ID=T5.Architecture_ID '||Astyle||'
)T8 '||strWhere1;
	
	
	end if;
	--print(sql1)
	execute immediate sql1;

else

	if not(strWhere1 is null or rtrim(strWhere1) = '' ) then
	 strWhere1:=' where '||strWhere1;
         end if;
	
	if CryStyle='Month'--月报表
	then	
	 sql1:='select '||strGetFields||' from (
	select '||EAstyle||'((select DictionaryValue_Value  from DictionaryValue as b where T4.Architecture_Style=b.DictionaryValue_Num and Dictionary_ID=3)  )Architecture_Stylename,T4.Architecture_ID,T5.Architecture_Name,T5.Architecture_Num,Area_Name,ZGross,SelectTime from Architecture T4 inner join
(select Architecture_ID,(select Architecture_Name from Architecture as a where T2.Architecture_ID=a.Architecture_ID)Architecture_Name,(select Architecture_Num from Architecture a where T2.Architecture_ID=a.Architecture_ID)Architecture_Num,(select Area_Name from Area b where T2.Area_ID=b.Area_ID)Area_Name,sum(ZGross)ZGross,SelectTime from AmMeter T2 inner join 
(select AmMeter_ID,ZGross,SelectTime from T_MonthAm '||TimeList1||')as T1 on T1.AmMeter_ID=T2.AmMeter_ID  '||SelectWhere1||where2||' group by Architecture_ID,SelectTime,Area_ID)T5 on T4.Architecture_ID=T5.Architecture_ID '||Astyle||'
)T8 '||strWhere1;
	
	elsif CryStyle='Year'--年报表
	then
	 sql1:='select '||strGetFields||' from (
	select '||EAstyle||'((select DictionaryValue_Value  from DictionaryValue as b where T4.Architecture_Style=b.DictionaryValue_Num and Dictionary_ID=3)  )Architecture_Stylename,T4.Architecture_ID,T5.Architecture_Name,T5.Architecture_Num,Area_Name,ZGross,SelectTime from Architecture T4 inner join
(select Architecture_ID,(select Architecture_Name from Architecture as a where T2.Architecture_ID=a.Architecture_ID)Architecture_Name,(select Architecture_Num from Architecture a where T2.Architecture_ID=a.Architecture_ID)Architecture_Num,(select Area_Name from Area b where T2.Area_ID=b.Area_ID)Area_Name,sum(ZGross)ZGross,TYear as SelectTime from AmMeter T2 inner join  
(select AmMeter_ID,sum(ZGross) ZGross,TYear from (select AmMeter_ID,ZGross,substring(SelectTime,1,4) TYear from T_MonthAm)T1  group by AmMeter_ID,TYear)as T1 on T1.AmMeter_ID=T2.AmMeter_ID '||SelectWhere1||where2||' group by Architecture_ID,TYear,Area_ID)T5 on T4.Architecture_ID=T5.Architecture_ID '||Astyle||'
)T8 '||strWhere1;		
	end if;
	--print(sql1)
	if PageSize<>0
	then		 
		 keyFldName:='Architecture_ID';
	/* exec SearchGetDataByPage sql1,orderFldDesc,keyFldName,PageSize,PageIndex  */
	
	else
	
		execute immediate sql1;
	end if;
end if;
end;










CREATE or replace procedure D_ArchitectureStatisics
(
SelectWhere varchar default '',--搜索的条件
Architecture_Style varchar default '0',
Energy_Style varchar default '',
Energy_Style1 varchar default '',
TimeList varchar default '',--搜索的条件
CryStyle varchar default 'Day',--报表统计类型
strGetFields varchar default  '*', 
doCount number default  0,--返回记录总数, 非 0 值则返回
orderFldDesc  varchar default '',--排序字段及排序方向，如addDate desc,id desc（排序字段需通过selectOrderFldName指定，可为空） 
PageSize     int default  10,           -- 页尺寸 ,如果为 0 不执行分页功能
PageIndex    int default  1,            -- 页码 
strWhere     varchar default  ''  -- 查询条件(注意: 不要加where) 
)
as
 sql1 varchar(8000);--sql=>sql1
 SelectWhere1 varchar(100);
 TimeList1 varchar(200);
 strWhere1 varchar(1000);
 keyFldName      varchar(80);  -- 主键字段 
 Astyle varchar(50);
 where2 varchar(200);
 EAstyle varchar(200);
 b1 varchar(20);
 b2 varchar(20);
begin

 SelectWhere1:=SelectWhere;
 TimeList1 :=TimeList;
 strWhere1 :=strWhere;
 sql1:='';
if not(SelectWhere1 is null or rtrim(SelectWhere1) = '' ) then
 SelectWhere1:=' where '||SelectWhere1;
 end if;

if Architecture_Style is null or rtrim(Architecture_Style) = ''or Architecture_Style='0' 
then
 Astyle:='';

else
 Astyle:=' and Architecture_Style='''||Architecture_Style||'''';
end if;


if Energy_Style is null or rtrim(Energy_Style) = ''or Energy_Style='0' 
then
 EAstyle:='''全部能耗'' as Energy_Subentry,''0'' Energy_ID,''全部能耗'' as Energy_Subentry1,''0'' Energy_ID1,';
 where2:='';
else
select DictionaryValue_Value into b1 from DictionaryValue where Dictionary_ID=7 and DictionaryValue_Num=Energy_Style;
	if Energy_Style1 is null or rtrim(Energy_Style1) = ''or Energy_Style1='0' 
              then	
		 EAstyle:=''''||b1||''' as Energy_Subentry,'''||Energy_Style||''' Energy_ID,''全部能耗'' as Energy_Subentry1,''0'' Energy_ID1,';
                 where2:=' and substring(AmMeter_Num,14,1)='''||Energy_Style||'''';             
	else
           select DictionaryValue_Value into b2 from DictionaryValue where Dictionary_ID=8 and DictionaryValue_Num=Energy_Style1;
           EAstyle:=''''||b1||''' as Energy_Subentry,'''||Energy_Style||''' Energy_ID,'''||b2||''' as Energy_Subentry1,'''||Energy_Style1||''' Energy_ID1,';
            where2:=' and substring(AmMeter_Num,14,1)='''||Energy_Style||''' and substring(AmMeter_Num,14,2)='''||Energy_Style1||'''' ;
	end if;

end if;

if doCount=0 
then
	if not(strWhere1 is null or rtrim(strWhere1) = '' ) then
	 strWhere1:=' where '||strWhere1;
          end if;

	if CryStyle='Day'--日报表 
	then	
	 sql1:='select count(Architecture_ID) from (
	select '||EAstyle||'T2.Architecture_ID,Architecture_Num,(select Area_Name from Area as b where T3.Area_ID=b.Area_ID) Area_Name, Architecture_Name,(select DictionaryValue_Value  from DictionaryValue as b where T3.Architecture_Style=b.DictionaryValue_Num and Dictionary_ID=3) Architecture_Stylename,Architecture_Storey,ZGross,JGross,FGross,PGross,GGross from
(select Architecture_ID,sum(ZGross)ZGross,sum(JGross)JGross,sum(FGross)FGross,sum(PGross)PGross,sum(GGross)GGross from 
(select Architecture_ID,ZGross,JGross,FGross,PGross,GGross from T_DayAm as a ,AmMeter as b where a.AmMeter_ID=b.AmMeter_ID and '||TimeList||where2||')T1 '||SelectWhere1||'
group by Architecture_ID)as T2 ,Architecture as T3 where T2.Architecture_ID=T3.Architecture_ID '||Astyle||')T '||strWhere1;
	
	elsif CryStyle='Month'--月报表
	then
	 sql1:='select count(Architecture_ID) from (
	select '||EAstyle||'T2.Architecture_ID,Architecture_Num,(select Area_Name from Area as b where T3.Area_ID=b.Area_ID) Area_Name, Architecture_Name,(select DictionaryValue_Value  from DictionaryValue as b where T3.Architecture_Style=b.DictionaryValue_Num and Dictionary_ID=3) Architecture_Stylename,Architecture_Storey,ZGross,JGross,FGross,PGross,GGross from
(select Architecture_ID,sum(ZGross)ZGross,sum(JGross)JGross,sum(FGross)FGross,sum(PGross)PGross,sum(GGross)GGross from 
(select Architecture_ID,ZGross,JGross,FGross,PGross,GGross from T_MonthAm as a ,AmMeter as b where a.AmMeter_ID=b.AmMeter_ID  and '||TimeList||where2||' )T1 '||SelectWhere1||'
group by Architecture_ID)as T2 ,Architecture as T3 where T2.Architecture_ID=T3.Architecture_ID  '||Astyle||')T '||strWhere1;
	elsif CryStyle='Year'--年报表
	then

	 sql1:='select count(Architecture_ID) from (
	select '||EAstyle||'T2.Architecture_ID,Architecture_Num,(select Area_Name from Area as b where T3.Area_ID=b.Area_ID) Area_Name, Architecture_Name,(select DictionaryValue_Value  from DictionaryValue as b where T3.Architecture_Style=b.DictionaryValue_Num and Dictionary_ID=3) Architecture_Stylename,Architecture_Storey,ZGross,JGross,FGross,PGross,GGross from
(select Architecture_ID,sum(ZGross)ZGross,sum(JGross)JGross,sum(FGross)FGross,sum(PGross)PGross,sum(GGross)GGross from 
(select Architecture_ID,ZGross,JGross,FGross,PGross,GGross from T_MonthAm as a ,AmMeter as b where a.AmMeter_ID=b.AmMeter_ID and  '||TimeList||where2||' )T1 '||SelectWhere1||'
group by Architecture_ID)as T2 ,Architecture as T3 where T2.Architecture_ID=T3.Architecture_ID  '||Astyle||')T '||strWhere1;

	elsif CryStyle='Hour'--小时报表
	then
	 sql1:='select count(Architecture_ID) from (
	select '||EAstyle||'T2.Architecture_ID,Architecture_Num,(select Area_Name from Area as b where T3.Area_ID=b.Area_ID) Area_Name, Architecture_Name,(select DictionaryValue_Value  from DictionaryValue as b where T3.Architecture_Style=b.DictionaryValue_Num and Dictionary_ID=3) Architecture_Stylename,Architecture_Storey,ZGross,JGross,FGross,PGross,GGross from
(select Architecture_ID,sum(ZGross)ZGross,sum(JGross)JGross,sum(FGross)FGross,sum(PGross)PGross,sum(GGross)GGross from 
(select a.AmMeter_ID,Area_ID,Architecture_ID,ZGross,JGross,FGross,PGross,GGross from T_HourAm as a ,AmMeter as b where a.AmMeter_ID=b.AmMeter_ID and '||TimeList||where2||' )T1 '||SelectWhere1||'
group by Architecture_ID)as T2 ,Architecture as T3 where T2.Architecture_ID=T3.Architecture_ID  '||Astyle||')T '||strWhere1;
	
	end if;

	execute immediate sql1;
else

	if not(strWhere1 is null or rtrim(strWhere1) = '' ) then
	 strWhere1:=' where '||strWhere1;
          end if;

	if CryStyle='Day'--日报表
	then	
	 sql1:='select '||strGetFields||' from (
	select '||EAstyle||'T2.Architecture_ID,Architecture_Num,(select Area_Name from Area as b where T3.Area_ID=b.Area_ID) Area_Name, Architecture_Name,(select DictionaryValue_Value  from DictionaryValue as b where T3.Architecture_Style=b.DictionaryValue_Num and Dictionary_ID=3) Architecture_Stylename,Architecture_Storey,ZGross,JGross,FGross,PGross,GGross from
(select Architecture_ID,sum(ZGross)ZGross,sum(JGross)JGross,sum(FGross)FGross,sum(PGross)PGross,sum(GGross)GGross from 
(select Architecture_ID,ZGross,JGross,FGross,PGross,GGross from T_DayAm as a ,AmMeter as b where a.AmMeter_ID=b.AmMeter_ID and '||TimeList||where2||' )T1 '||SelectWhere1||'
group by Architecture_ID)as T2 ,Architecture as T3 where T2.Architecture_ID=T3.Architecture_ID  '||Astyle||')T '||strWhere1;
	
	elsif CryStyle='Month'--月报表
	then
	
	 sql1:='select '||strGetFields||' from (
	select '||EAstyle||'T2.Architecture_ID,Architecture_Num,(select Area_Name from Area as b where T3.Area_ID=b.Area_ID) Area_Name, Architecture_Name,(select DictionaryValue_Value  from DictionaryValue as b where T3.Architecture_Style=b.DictionaryValue_Num and Dictionary_ID=3) Architecture_Stylename,Architecture_Storey,ZGross,JGross,FGross,PGross,GGross from
(select Architecture_ID,sum(ZGross)ZGross,sum(JGross)JGross,sum(FGross)FGross,sum(PGross)PGross,sum(GGross)GGross from 
(select Architecture_ID,ZGross,JGross,FGross,PGross,GGross from T_MonthAm as a ,AmMeter as b where a.AmMeter_ID=b.AmMeter_ID and '||TimeList||where2||' )T1 '||SelectWhere1||'
group by Architecture_ID)as T2 ,Architecture as T3 where T2.Architecture_ID=T3.Architecture_ID  '||Astyle||')T '||strWhere1;

	
	elsif CryStyle='Year'--年报表
	then

	 sql1:='select '||strGetFields||' from (
	select '||EAstyle||'T2.Architecture_ID,Architecture_Num,(select Area_Name from Area as b where T3.Area_ID=b.Area_ID) Area_Name, Architecture_Name,(select DictionaryValue_Value  from DictionaryValue as b where T3.Architecture_Style=b.DictionaryValue_Num and Dictionary_ID=3) Architecture_Stylename,Architecture_Storey,ZGross,JGross,FGross,PGross,GGross from
(select Architecture_ID,sum(ZGross)ZGross,sum(JGross)JGross,sum(FGross)FGross,sum(PGross)PGross,sum(GGross)GGross from 
(select Architecture_ID,ZGross,JGross,FGross,PGross,GGross from T_MonthAm as a ,AmMeter as b where a.AmMeter_ID=b.AmMeter_ID and '||TimeList||where2||' )T1 '||SelectWhere1||'
group by Architecture_ID)as T2 ,Architecture as T3 where T2.Architecture_ID=T3.Architecture_ID  '||Astyle||')T '||strWhere1;
		
	elsif CryStyle='Hour'--小时报表
	then
	 sql1:='select '||strGetFields||' from (
	select '||EAstyle||'T2.Architecture_ID,Architecture_Num,(select Area_Name from Area as b where T3.Area_ID=b.Area_ID) Area_Name, Architecture_Name,(select DictionaryValue_Value  from DictionaryValue as b where T3.Architecture_Style=b.DictionaryValue_Num and Dictionary_ID=3) Architecture_Stylename,Architecture_Storey,ZGross,JGross,FGross,PGross,GGross from
(select Architecture_ID,sum(ZGross)ZGross,sum(JGross)JGross,sum(FGross)FGross,sum(PGross)PGross,sum(GGross)GGross from 
(select Architecture_ID,ZGross,JGross,FGross,PGross,GGross from T_HourAm as a ,AmMeter as b where a.AmMeter_ID=b.AmMeter_ID and '||TimeList||where2||' )T1 '||SelectWhere1||'
group by Architecture_ID)as T2 ,Architecture as T3 where T2.Architecture_ID=T3.Architecture_ID  '||Astyle||')T '||strWhere1;
	
	end if;

	if PageSize<>0
	then
		 keyFldName:='Architecture_ID' ;
--print sql
	/*	exec SearchGetDataByPage sql1,orderFldDesc,keyFldName,PageSize,PageIndex */
	
	else
	
		execute immediate sql1;
	end if;
end if;
end;










CREATE or replace procedure D_ConsumerChainTime
(
SelectWhere varchar default '',--搜索的条件
TimeList varchar default '',--搜索的条件
TimeList2 varchar default '',--搜索的条件
CryStyle varchar default 'Month',--报表统计类型
strGetFields varchar default '*', 
doCount number default 0,--返回记录总数, 非 0 值则返回
orderFldDesc  varchar default '',--排序字段及排序方向，如addDate desc,id desc（排序字段需通过selectOrderFldName指定，可为空） 
PageSize     int default 10,           -- 页尺寸 ,如果为 0 不执行分页功能
PageIndex    int default 1,            -- 页码 
strWhere     varchar default ''  -- 查询条件(注意: 不要加where) 
)
as
 sql1 varchar(6000);--sql=>sql1
 SelectWhere1 varchar(100);
 TimeList1 varchar(200);--代替了TimeList
 strWhere1 varchar(1000);
 keyFldName      varchar(80);  -- 主键字段 
 TimeList2r varchar(200); --代替了TimeList2
begin

 sql1:='';
 SelectWhere1:=SelectWhere;
 TimeList1 :=TimeList;
 strWhere1 :=strWhere;
 TimeList2r:=TimeList2;
if not(SelectWhere1 is null or rtrim(SelectWhere1) = '' ) then
 SelectWhere1:=' where '||SelectWhere1;
end if;

if not(TimeList1 is null or rtrim(TimeList1) = '' ) then
 TimeList1:=' where '||TimeList1;
end if;

if not(TimeList2r is null or rtrim(TimeList2r) = '' ) then
 TimeList2r:=' where '||TimeList2r;
end if;
if doCount=0 
then
	if not(strWhere1 is null or rtrim(strWhere1) = '' ) then
	 strWhere1:=' where '||strWhere1;
           end if;
	
	if CryStyle='Month'--月报表
	then	
	 sql1:='select count(Meter_ID) from (
select T4.AmMeter_ID Meter_ID,T4.Architecture_ID,BZGross,SZGross,CGross,BFGross,AmMeter_Num Meter_Num,AmMeter_Name Meter_Name,AmMeter_485Address Meter_485Address,ConsumerNum,ConsumerName,
ConsumerAddress,Area_ID from AmMeter T4 inner join
(select T1.AmMeter_ID,T1.ZGross BZGross,T2.ZGross SZGross,(T1.ZGross-T2.ZGross)CGross,cast(cast((T1.ZGross-T2.ZGross)/T2.ZGross*100 as numeric(12,3)) as varchar(20))||''%'' BFGross   from
(select AmMeter_ID,ZGross,SelectTime from T_MonthAm  '||TimeList||')T1 inner join 
(select AmMeter_ID,ZGross,SelectTime from T_MonthAm '||TimeList2r||')T2 on T1.AmMeter_ID=T2.AmMeter_ID)T3 
on T4.AmMeter_ID=T3.AmMeter_ID '||SelectWhere1||'
) T5'||strWhere1;
	
	elsif CryStyle='Year'--年报表
	then

	 sql1:='select count(Meter_ID) from (
	select T4.AmMeter_ID Meter_ID,T4.Architecture_ID,BZGross,SZGross,CGross,BFGross,AmMeter_Num Meter_Num,AmMeter_Name Meter_Name,AmMeter_485Address Meter_485Address,ConsumerNum,ConsumerName,
ConsumerAddress,Area_ID from AmMeter T4 inner join
(select T1.AmMeter_ID,T1.ZGross BZGross,T2.ZGross SZGross,(T1.ZGross-T2.ZGross)CGross,cast(cast((T1.ZGross-T2.ZGross)/T2.ZGross*100 as numeric(12,3)) as varchar(20))||''%'' BFGross  from
(select AmMeter_ID,sum(ZGross)ZGross from T_MonthAm '||TimeList||' group by AmMeter_ID)T1 inner join 
(select AmMeter_ID,sum(ZGross)ZGross from T_MonthAm '||TimeList2r||' group by AmMeter_ID)T2 on T1.AmMeter_ID=T2.AmMeter_ID)T3 
on T4.AmMeter_ID=T3.AmMeter_ID  '||SelectWhere1||'
)T '||strWhere1;
	
	
	end if;
	--print(sql1)
	execute immediate sql1;
else

	if not(strWhere1 is null or rtrim(strWhere1) = '' )  then
	 strWhere1:=' where '||strWhere1;
          end if;
	
	if CryStyle='Month'--月报表
	then
	
	 sql1:='select '||strGetFields||' from (
select T4.AmMeter_ID Meter_ID,T4.Architecture_ID,BZGross,SZGross,CGross,BFGross,AmMeter_Num Meter_Num,AmMeter_Name Meter_Name,AmMeter_485Address Meter_485Address,ConsumerNum,ConsumerName,
ConsumerAddress,Area_ID from AmMeter T4 inner join
(select T1.AmMeter_ID,T1.ZGross BZGross,T2.ZGross SZGross,(T1.ZGross-T2.ZGross)CGross,cast(cast((T1.ZGross-T2.ZGross)/T2.ZGross*100 as numeric(12,3)) as varchar(20))||''%'' BFGross   from
(select AmMeter_ID,ZGross,SelectTime from T_MonthAm  '||TimeList||')T1 inner join 
(select AmMeter_ID,ZGross,SelectTime from T_MonthAm '||TimeList2r||')T2 on T1.AmMeter_ID=T2.AmMeter_ID)T3 
on T4.AmMeter_ID=T3.AmMeter_ID '||SelectWhere1||'
)T '||strWhere1;
	
	
	elsif CryStyle='Year'--年报表
	then

	 sql1:='select '||strGetFields||' from (
		select T4.AmMeter_ID Meter_ID,T4.Architecture_ID,BZGross,SZGross,CGross,BFGross,AmMeter_Num Meter_Num,AmMeter_Name Meter_Name,AmMeter_485Address Meter_485Address,ConsumerNum,ConsumerName,
ConsumerAddress,Area_ID from AmMeter T4 inner join
(select T1.AmMeter_ID,T1.ZGross BZGross,T2.ZGross SZGross,(T1.ZGross-T2.ZGross)CGross,cast(cast((T1.ZGross-T2.ZGross)/T2.ZGross*100 as numeric(12,3)) as varchar(20))||''%'' BFGross   from
(select AmMeter_ID,sum(ZGross)ZGross from T_MonthAm '||TimeList||' group by AmMeter_ID)T1 inner join 
(select AmMeter_ID,sum(ZGross)ZGross from T_MonthAm '||TimeList2r||' group by AmMeter_ID)T2 on T1.AmMeter_ID=T2.AmMeter_ID)T3 
on T4.AmMeter_ID=T3.AmMeter_ID  '||SelectWhere1||'
)T '||strWhere1;
	
	
	end if;
	
	if PageSize<>0
	then
		 keyFldName:='Meter_ID';
	/*	exec SearchGetDataByPage sql1,orderFldDesc,keyFldName,PageSize,PageIndex  */
	
	else
	
		execute immediate sql1;
	end if;
end if;
end;













CREATE or replace procedure D_ConsumerSameTime
(
SelectWhere varchar default '',--搜索的条件
TimeList varchar default '',--搜索的条件
CryStyle varchar default 'Month',--报表统计类型
strGetFields varchar default '*', 
doCount number default 0,--返回记录总数, 非 0 值则返回
orderFldDesc  varchar default'',--排序字段及排序方向，如addDate desc,id desc（排序字段需通过selectOrderFldName指定，可为空） 
PageSize     int default 10,           -- 页尺寸 ,如果为 0 不执行分页功能
PageIndex    int default 1,            -- 页码 
strWhere     varchar default ''  -- 查询条件(注意: 不要加where) 
)
as
 sql1 varchar(6000);--sql=>sql1
 SelectWhere1 varchar(100);
 TimeList1 varchar(200);
 strWhere1 varchar(1000);
 keyFldName      varchar(80);  -- 主键字段 
begin

 sql1:='';
 SelectWhere1:=SelectWhere;
 TimeList1 :=TimeList;
 strWhere1 :=strWhere;
if not(SelectWhere1 is null or rtrim(SelectWhere1) = '' )  then
 SelectWhere1:=' where '||SelectWhere1;
end if;

if not(TimeList1 is null or rtrim(TimeList1) = '' )  then
 TimeList1:=' where '||TimeList1;
end if;

if doCount=0 
then
	if not(strWhere1 is null or rtrim(strWhere1) = '' ) then
	 strWhere1:=' where '||strWhere1;
          end if;
	
	if CryStyle='Month'--月报表
	then
	
	 sql1:='select count(Meter_ID) from (
select T2.AmMeter_ID Meter_ID,T2.Architecture_ID,ZValue,ZGross,AmMeter_Num Meter_Num,AmMeter_Name Meter_Name,AmMeter_485Address Meter_485Address,SelectTime,ConsumerNum,ConsumerName,ConsumerAddress,Area_ID from AmMeter T2 inner join 
(select AmMeter_ID,ZGross,SelectTime from T_MonthAm '||TimeList1||')as T1 on T1.AmMeter_ID=T2.AmMeter_ID  '||SelectWhere1||'
) T3'||strWhere1;
	
	elsif CryStyle='Year'--年报表
	then

	 sql1:='select count(Meter_ID) from (
	select T2.AmMeter_ID Meter_ID,T2.Architecture_ID,ZGross,TYear as SelectTime,AmMeter_Num Meter_Num,AmMeter_Name Meter_Name,AmMeter_485Address Meter_485Address,ConsumerNum,ConsumerName,ConsumerAddress,Area_ID from AmMeter T2 inner join 
(select AmMeter_ID,sum(ZGross) ZGross,TYear from (select AmMeter_ID,ZGross,substring(SelectTime,1,4) TYear from T_MonthAm)T1  group by AmMeter_ID,TYear)as T1 on T1.AmMeter_ID=T2.AmMeter_ID '||SelectWhere1||'
)T '||strWhere1;
	
	
	end if;
	--print(sql1)
	execute immediate sql1;

else

	if not(strWhere1 is null or rtrim(strWhere1) = '' ) then
	 strWhere1:=' where '||strWhere1;
        end if;
	
	if CryStyle='Month'--月报表
	then
	
	 sql1:='select '||strGetFields||' from (
	select T2.AmMeter_ID Meter_ID,T2.Architecture_ID,ZValue,ZGross,AmMeter_Num Meter_Num,AmMeter_Name Meter_Name,AmMeter_485Address Meter_485Address,SelectTime,ConsumerNum,ConsumerName,ConsumerAddress,Area_ID from AmMeter T2 inner join 
(select AmMeter_ID,ZGross,SelectTime from T_MonthAm '||TimeList1||')as T1 on T1.AmMeter_ID=T2.AmMeter_ID  '||SelectWhere1||'
)T '||strWhere1;
	
	
	elsif CryStyle='Year'--年报表
	then

	 sql1:='select '||strGetFields||' from (
	select T2.AmMeter_ID Meter_ID,T2.Architecture_ID,ZGross,TYear as SelectTime,AmMeter_Num Meter_Num,AmMeter_Name Meter_Name,AmMeter_485Address Meter_485Address,ConsumerNum,ConsumerName,ConsumerAddress,Area_ID from AmMeter T2 inner join 
(select AmMeter_ID,sum(ZGross) ZGross,TYear from (select AmMeter_ID,ZGross,substring(SelectTime,1,4) TYear from T_MonthAm)T1  group by AmMeter_ID,TYear)as T1 on T1.AmMeter_ID=T2.AmMeter_ID '||SelectWhere1||'
)T '||strWhere1;
	
	
	end if;

	if PageSize<>0
	then
		 keyFldName:='Meter_ID';
	/*	exec SearchGetDataByPage sql1,orderFldDesc,keyFldName,PageSize,PageIndex  */
	
	else
	
		execute immediate sql1;
	end if;

end if;
end;










CREATE or replace procedure D_ConsumerStatisics
(
SelectWhere varchar default '',--搜索的条件
TimeList varchar default '',--搜索的条件
CryStyle varchar default 'Day',--报表统计类型
strGetFields varchar default  '*', 
doCount number default  0,--返回记录总数, 非 0 值则返回
orderFldDesc  varchar default '',--排序字段及排序方向，如addDate desc,id desc（排序字段需通过selectOrderFldName指定，可为空） 
PageSize     int default  10,           -- 页尺寸 ,如果为 0 不执行分页功能
PageIndex    int default 1,            -- 页码 
strWhere     varchar default  ''  -- 查询条件(注意: 不要加where) 
)
as
 sql1 varchar(6000);--sql=>sql1
 SelectWhere1 varchar(100);
 TimeList1 varchar(200);
 strWhere1 varchar(1000);
 keyFldName      varchar(80);  -- 主键字段 
begin

 sql1:='';
 SelectWhere1:=SelectWhere;
 TimeList1 :=TimeList;
 strWhere1 :=strWhere;
if not(SelectWhere1 is null or rtrim(SelectWhere1) = '' ) then
 SelectWhere1:=' where '||SelectWhere1;
end if;

if not(TimeList1 is null or rtrim(TimeList1) = '' ) then
 TimeList1:=' where '||TimeList1;
end if;

if doCount=0 
then
	if not(strWhere1 is null or rtrim(strWhere1) = '' )  then
	 strWhere1:=' where '||strWhere1;
         end if;
	if CryStyle='Day'--日报表
	then
	
	 sql1:='select count(Meter_ID) from (
	select T1.*,T2.ZGross,T2.JGross,T2.FGross,T2.PGross,T2.GGross from 
	(select AmMeter_ID Meter_ID,(select Area_Name from Area as b where a.Area_ID=b.Area_ID) Area_Name,(select Architecture_Name from Architecture as b where a.Architecture_ID=b.Architecture_ID)Architecture_Name,AmMeter_Num Meter_Num,AmMeter_Name Meter_Name,AmMeter_485Address Meter_485Address,ConsumerNum,ConsumerName,ConsumerAddress,(select DictionaryValue_Value from DictionaryValue as b where DictionaryValue_ID=a.Partment)PartmentName,Floor as FloorName from AmMeter as a '||SelectWhere1||')T1,
	(select AmMeter_ID,ZGross,JGross,FGross,PGross,GGross from T_DayAm '||TimeList1||' )T2 where T1.Meter_ID=T2.AmMeter_ID)T '||strWhere1;
	
	
	elsif CryStyle='Month'--月报表
	then
	
	 sql1:='select count(Meter_ID) from (
	select T1.*,T2.ZGross,T2.JGross,T2.FGross,T2.PGross,T2.GGross from 
	(select AmMeter_ID Meter_ID,(select Area_Name from Area as b where a.Area_ID=b.Area_ID) Area_Name,(select Architecture_Name from Architecture as b where a.Architecture_ID=b.Architecture_ID)Architecture_Name,AmMeter_Num Meter_Num,AmMeter_Name Meter_Name,AmMeter_485Address Meter_485Address,ConsumerNum,ConsumerName,ConsumerAddress,(select DictionaryValue_Value from DictionaryValue as b where DictionaryValue_ID=a.Partment)PartmentName,Floor as FloorName from AmMeter as a '||SelectWhere1||')T1,
	(select AmMeter_ID,ZGross,JGross,FGross,PGross,GGross from T_MonthAm '||TimeList1||' )T2 where T1.Meter_ID=T2.AmMeter_ID)T '||strWhere1;
	
	
	elsif CryStyle='Year'--年报表
	then

	 sql1:='select count(Meter_ID) from (
	select T1.*,T2.ZGross,T2.JGross,T2.FGross,T2.PGross,T2.GGross from 
	(select AmMeter_ID Meter_ID,(select Area_Name from Area as b where a.Area_ID=b.Area_ID) Area_Name,(select Architecture_Name from Architecture as b where a.Architecture_ID=b.Architecture_ID)Architecture_Name,AmMeter_Num Meter_Num,AmMeter_Name Meter_Name,AmMeter_485Address Meter_485Address,ConsumerNum,ConsumerName,ConsumerAddress,(select DictionaryValue_Value from DictionaryValue as b where DictionaryValue_ID=a.Partment)PartmentName,Floor as FloorName from AmMeter as a '||SelectWhere1||')T1,
	(select AmMeter_ID,sum(ZGross)ZGross,sum(JGross)JGross,sum(FGross)FGross,sum(PGross)PGross,sum(GGross)GGross from T_MonthAm '||TimeList1||' group by AmMeter_ID)T2 where T1.Meter_ID=T2.AmMeter_ID)T '||strWhere1;
	
	
	
	elsif CryStyle='Hour'--小时报表
	then
	 sql1:='select count(Meter_ID) from (
	select T1.*,T2.ZGross,T2.JGross,T2.FGross,T2.PGross,T2.GGross from 
	(select AmMeter_ID Meter_ID,(select Area_Name from Area as b where a.Area_ID=b.Area_ID) Area_Name,(select Architecture_Name from Architecture as b where a.Architecture_ID=b.Architecture_ID)Architecture_Name,AmMeter_Num Meter_Num,AmMeter_Name Meter_Name,AmMeter_485Address Meter_485Address,ConsumerNum,ConsumerName,ConsumerAddress,(select DictionaryValue_Value from DictionaryValue as b where DictionaryValue_ID=a.Partment)PartmentName,Floor as FloorName from AmMeter as a '||SelectWhere1||')T1,
	(select AmMeter_ID,ZGross,JGross,FGross,PGross,GGross from T_HourAm '||TimeList1||' )T2 where T1.Meter_ID=T2.AmMeter_ID)T '||strWhere1;
	
	
	end if;
	execute immediate sql1;
else

	if not(strWhere1 is null or rtrim(strWhere1) = '' )  then
	 strWhere1:=' where '||strWhere1;
         end if;
	if CryStyle='Day'--日报表
	then
	
	 sql1:='select '||strGetFields||' from (
	select T1.*,T2.ZGross,T2.JGross,T2.FGross,T2.PGross,T2.GGross from 
	(select AmMeter_ID Meter_ID,(select Area_Name from Area as b where a.Area_ID=b.Area_ID) Area_Name,(select Architecture_Name from Architecture as b where a.Architecture_ID=b.Architecture_ID)Architecture_Name,AmMeter_Num Meter_Num,AmMeter_Name Meter_Name,AmMeter_485Address Meter_485Address,ConsumerNum,ConsumerName,ConsumerAddress,(select DictionaryValue_Value from DictionaryValue as b where DictionaryValue_ID=a.Partment)PartmentName,Floor as FloorName from AmMeter as a '||SelectWhere1||')T1,
	(select AmMeter_ID,ZGross,JGross,FGross,PGross,GGross from T_DayAm '||TimeList1||' )T2 where T1.Meter_ID=T2.AmMeter_ID)T '||strWhere1;
	
	
	elsif CryStyle='Month'--月报表
	then
	
	 sql1:='select '||strGetFields||' from (
	select T1.*,T2.ZGross,T2.JGross,T2.FGross,T2.PGross,T2.GGross from 
	(select AmMeter_ID Meter_ID,(select Area_Name from Area as b where a.Area_ID=b.Area_ID) Area_Name,(select Architecture_Name from Architecture as b where a.Architecture_ID=b.Architecture_ID)Architecture_Name,AmMeter_Num Meter_Num,AmMeter_Name Meter_Name,AmMeter_485Address Meter_485Address,ConsumerNum,ConsumerName,ConsumerAddress,(select DictionaryValue_Value from DictionaryValue as b where DictionaryValue_ID=a.Partment)PartmentName,Floor as FloorName from AmMeter as a '||SelectWhere1||')T1,
	(select AmMeter_ID,ZGross,JGross,FGross,PGross,GGross from T_MonthAm '||TimeList1||' )T2 where T1.Meter_ID=T2.AmMeter_ID)T '||strWhere1;
	
	
	elsif CryStyle='Year'--年报表
	then

	 sql1:='select '||strGetFields||' from (
	select T1.*,T2.ZGross,T2.JGross,T2.FGross,T2.PGross,T2.GGross from 
	(select AmMeter_ID Meter_ID,(select Area_Name from Area as b where a.Area_ID=b.Area_ID) Area_Name,(select Architecture_Name from Architecture as b where a.Architecture_ID=b.Architecture_ID)Architecture_Name,AmMeter_Num Meter_Num,AmMeter_Name Meter_Name,AmMeter_485Address Meter_485Address,ConsumerNum,ConsumerName,ConsumerAddress,(select DictionaryValue_Value from DictionaryValue as b where DictionaryValue_ID=a.Partment)PartmentName,Floor as FloorName from AmMeter as a '||SelectWhere1||')T1,
	(select AmMeter_ID,sum(ZGross)ZGross,sum(JGross)JGross,sum(FGross)FGross,sum(PGross)PGross,sum(GGross)GGross from T_MonthAm '||TimeList1||' group by AmMeter_ID)T2 where T1.Meter_ID=T2.AmMeter_ID)T '||strWhere1;
	
	
	
	elsif CryStyle='Hour'--小时报表
	then
	 sql1:='select '||strGetFields||' from (
	select T1.*,T2.ZGross,T2.JGross,T2.FGross,T2.PGross,T2.GGross from 
	(select AmMeter_ID Meter_ID,(select Area_Name from Area as b where a.Area_ID=b.Area_ID) Area_Name,(select Architecture_Name from Architecture as b where a.Architecture_ID=b.Architecture_ID)Architecture_Name,AmMeter_Num Meter_Num,AmMeter_Name Meter_Name,AmMeter_485Address Meter_485Address,ConsumerNum,ConsumerName,ConsumerAddress,(select DictionaryValue_Value from DictionaryValue as b where DictionaryValue_ID=a.Partment)PartmentName,Floor as FloorName from AmMeter as a '||SelectWhere1||')T1,
	(select AmMeter_ID,ZGross,JGross,FGross,PGross,GGross from T_HourAm '||TimeList1||' )T2 where T1.Meter_ID=T2.AmMeter_ID)T '||strWhere1;
	
	end if;

	if PageSize<>0
	then
		 keyFldName:='Meter_ID';
	/*	exec SearchGetDataByPage sql1,orderFldDesc,keyFldName,PageSize,PageIndex  */
	
	else
	
		execute immediate sql1;
	end if;
end if;
end;









CREATE or replace  procedure D_CopyHistory
(
SelectWhere varchar default '',--搜索的条件
TimeList varchar default '',--搜索的条件
CryStyle varchar default 'Day',--报表统计类型
SelectStyle varchar default 'All',--报表统计类型
strGetFields varchar default  '*', 
doCount number default 0,--返回记录总数, 非 0 值则返回
orderFldDesc  varchar default '',--排序字段及排序方向，如addDate desc,id desc（排序字段需通过selectOrderFldName指定，可为空） 
PageSize     int default 10,           -- 页尺寸 ,如果为 0 不执行分页功能
PageIndex    int default 1,            -- 页码 
strWhere     varchar default ''  -- 查询条件(注意: 不要加where) 
)
as
 where2 varchar(200);
 sql1 varchar(6000);--sql=>sql1
 SelectWhere1 varchar(100);
 TimeList1 varchar(200);
 strWhere1 varchar(1000);
 keyFldName      varchar(80);  -- 主键字段 
begin

 SelectWhere1:=SelectWhere;
 TimeList1 :=TimeList;
 strWhere1 :=strWhere;
 sql1:='';
if not(SelectWhere1 is null or rtrim(SelectWhere1) = '' ) then
 SelectWhere1:=' where '||SelectWhere1;
end if;
if not(TimeList1 is null or rtrim(TimeList1) = '' )  then
 TimeList1:=' where '||TimeList1;
end if;
if SelectStyle='Zero'--0表码
then
 where2:=' and ZValueZY=0';

else

 where2:='';
end if;

if doCount=0 
then
	if not(strWhere1 is null or rtrim(strWhere1) = '' ) then
	 strWhere1:=' where '||strWhere1;
          end if;
	if CryStyle='Day'--日报表
	then
	
	 sql1:='select count(AmMeter_ID) from (
		select a.AmMeter_ID,AmMeter_Name,AmMeter_Address,ConsumerNum,ConsumerName,(select Gather_Name from Gather as c where c.Gather_ID=b.Gather_ID)Gather_Name,(select Architecture_Name from Architecture as c where c.Architecture_ID=b.Architecture_ID)Architecture_Name,ValueTime,ZValueZY from (select AmMeter_ID,ValueTime,ZValueZY from AmMeterDayValue '||TimeList1||where2||')a,(select AmMeter_ID,AmMeter_Name,AmMeter_485Address as AmMeter_Address,ConsumerNum,ConsumerName,Gather_ID,Architecture_ID from AmMeter '||SelectWhere1||') as b where a.AmMeter_ID=b.AmMeter_ID
		)T '||strWhere1;
	
	elsif CryStyle='Hour'--小时报表
	then
	 sql1:='select count(AmMeter_ID) from (
		select a.AmMeter_ID,AmMeter_Name,AmMeter_Address,ConsumerNum,ConsumerName,(select Gather_Name from Gather as c where c.Gather_ID=b.Gather_ID)Gather_Name,(select Architecture_Name from Architecture as c where c.Architecture_ID=b.Architecture_ID)Architecture_Name,ValueTime,ZValueZY from (select AmMeter_ID,ValueTime,ZValueZY from AmMeterHourValue '||TimeList1||where2||')a,(select AmMeter_ID,AmMeter_Name,AmMeter_485Address as AmMeter_Address,ConsumerNum,ConsumerName,Gather_ID,Architecture_ID from AmMeter '||SelectWhere1||') as b where a.AmMeter_ID=b.AmMeter_ID
		)T '||strWhere1;
	end if;
	execute immediate sql1;

else

	if not(strWhere1 is null or rtrim(strWhere1) = '' ) then
	 strWhere1:=' where '||strWhere1;
         end if;

	if CryStyle='Day'--日报表
	then
	 sql1:='select '||strGetFields||' from (
		select a.AmMeter_ID,AmMeter_Name,AmMeter_Address,ConsumerNum,ConsumerName,(select Gather_Name from Gather as c where c.Gather_ID=b.Gather_ID)Gather_Name,(select Architecture_Name from Architecture as c where c.Architecture_ID=b.Architecture_ID)Architecture_Name,ValueTime,ZValueZY from (select AmMeter_ID,ValueTime,ZValueZY from AmMeterDayValue '||TimeList1||where2||')a,(select AmMeter_ID,AmMeter_Name,AmMeter_485Address as AmMeter_Address,ConsumerNum,ConsumerName,Gather_ID,Architecture_ID from AmMeter '||SelectWhere1||') as b where a.AmMeter_ID=b.AmMeter_ID
		)T '||strWhere1;
	
	elsif CryStyle='Hour'--小时报表
	then
	 sql1:='select '||strGetFields||' from (
			select a.AmMeter_ID,AmMeter_Name,AmMeter_Address,ConsumerNum,ConsumerName,(select Gather_Name from Gather as c where c.Gather_ID=b.Gather_ID)Gather_Name,(select Architecture_Name from Architecture as c where c.Architecture_ID=b.Architecture_ID)Architecture_Name,ValueTime,ZValueZY from (select AmMeter_ID,ValueTime,ZValueZY from AmMeterHourValue '||TimeList1||where2||')a,(select AmMeter_ID,AmMeter_Name,AmMeter_485Address as AmMeter_Address,ConsumerNum,ConsumerName,Gather_ID,Architecture_ID from AmMeter '||SelectWhere1||') as b where a.AmMeter_ID=b.AmMeter_ID
		)T '||strWhere1;
	end if;

	if PageSize<>0
	then
		
		 keyFldName:='AmMeter_ID';
	/*	exec SearchGetDataByPage sql1,orderFldDesc,keyFldName,PageSize,PageIndex  */
	
	else
	
		execute immediate sql1;
	end if;
end if;
end;












CREATE or replace procedure D_CopyNowE50F
(
SelectWhere varchar default '',--搜索的条件
CryStyle varchar default 'All',--报表统计类型
strGetFields varchar default  '*', 
doCount number default  0,--返回记录总数, 非 0 值则返回
orderFldDesc  varchar default  '',--排序字段及排序方向，如addDate desc,id desc（排序字段需通过selectOrderFldName指定，可为空） 
PageSize     int default  10,           -- 页尺寸 ,如果为 0 不执行分页功能
PageIndex    int default  1,            -- 页码 
strWhere     varchar default  ''  -- 查询条件(注意: 不要加where) 
)
as
 sql1 varchar(6000);--sql=>sql1
 SelectWhere1 varchar(100);
 strWhere1 varchar(1000);
 keyFldName      varchar(80);  -- 主键字段 
begin

 sql1:='';
SelectWhere1:=SelectWhere;
 strWhere1 :=strWhere;
if not(SelectWhere1 is null or rtrim(SelectWhere1) = '' ) then
 SelectWhere1:=' where '||SelectWhere1;
end if;

if doCount=0 
then
	if not(strWhere1 is null or rtrim(strWhere1) = '' ) then
	 strWhere1:=' where '||strWhere1;
         end if;

	if CryStyle='All'--全部数据
	then
	
	 sql1:='select count(AmMeter_ID) from (
	select a.AmMeter_ID,AmMeter_Name,AmMeter_485Address as AmMeter_Address,ConsumerNum,ConsumerName,(select Gather_Name from Gather as c where c.Gather_ID=b.Gather_ID)Gather_Name,(select Architecture_Name from Architecture as c where c.Architecture_ID=b.Architecture_ID)Architecture_Name,ValueTime,ZValueZY,(case MeterState_ID when ''1'' then ''成功'' else ''失败'' end)MeterState from (select AmMeter_ID,ValueTime,ZValueZY,MeterState_ID from AmMeterNowValue '||SelectWhere1||')a,AmMeter as b where a.AmMeter_ID=b.AmMeter_ID
	)T '||strWhere1;
	
	elsif CryStyle='Fail'--失败数据
	then
	
	 sql1:='select count(AmMeter_ID) from (
	select a.AmMeter_ID,AmMeter_Name,AmMeter_485Address as AmMeter_Address,ConsumerNum,ConsumerName,(select Gather_Name from Gather as c where c.Gather_ID=b.Gather_ID)Gather_Name,(select Architecture_Name from Architecture as c where c.Architecture_ID=b.Architecture_ID)Architecture_Name,ValueTime,ZValueZY,(case MeterState_ID when ''1'' then ''成功'' else ''失败'' end)MeterState from (select AmMeter_ID,ValueTime,ZValueZY,MeterState_ID from AmMeterNowValue '||SelectWhere1||' and MeterState_ID=''0'')a,AmMeter as b where a.AmMeter_ID=b.AmMeter_ID
	)T '||strWhere1;
	end if;
	execute immediate sql1;
else
	if not(strWhere1 is null or rtrim(strWhere1) = '' ) then
	 strWhere1:=' where '||strWhere1;
         end if;
	if CryStyle='All'--全部数据
	then
	
	 sql1:='select '||strGetFields||' from (
	select a.AmMeter_ID,AmMeter_Name,AmMeter_485Address as AmMeter_Address,ConsumerNum,ConsumerName,(select Gather_Name from Gather as c where c.Gather_ID=b.Gather_ID)Gather_Name,(select Architecture_Name from Architecture as c where c.Architecture_ID=b.Architecture_ID)Architecture_Name,ValueTime,ZValueZY,(case MeterState_ID when ''1'' then ''成功'' else ''失败'' end)MeterState from (select AmMeter_ID,ValueTime,ZValueZY,MeterState_ID from AmMeterNowValue '||SelectWhere1||')a,AmMeter as b where a.AmMeter_ID=b.AmMeter_ID
	)T '||strWhere1;
	
	
	elsif CryStyle='Fail'--失败数据
	then
	
	 sql1:='select '||strGetFields||' from (
	select a.AmMeter_ID,AmMeter_Name,AmMeter_485Address as AmMeter_Address,ConsumerNum,ConsumerName,(select Gather_Name from Gather as c where c.Gather_ID=b.Gather_ID)Gather_Name,(select Architecture_Name from Architecture as c where c.Architecture_ID=b.Architecture_ID)Architecture_Name,ValueTime,ZValueZY,(case MeterState_ID when ''1'' then ''成功'' else ''失败'' end)MeterState from (select AmMeter_ID,ValueTime,ZValueZY,MeterState_ID from AmMeterNowValue '||SelectWhere1||' and MeterState_ID=''0'')a,AmMeter as b where a.AmMeter_ID=b.AmMeter_ID
	)T '||strWhere1;
	end if;

	if PageSize<>0
	then
		
		 keyFldName:='AmMeter_ID';  
	/*	exec SearchGetDataByPage sql1,orderFldDesc,keyFldName,PageSize,PageIndex */
	
	else
	
		execute immediate sql1;
	end if;
end if;
end;







CREATE or replace procedure D_EnergyAllChainTime
(
SelectWhere varchar default '',--搜索的条件
TimeList varchar default '',--搜索日期条件本次
TimeList2 varchar default '',--搜索日期条件上次
CryStyle varchar default 'Day',--报表统计类型
strGetFields varchar default '*', 
selectarchinum varchar, --建筑分类
orderFldDesc  varchar default '',--排序字段及排序方向，如addDate desc,id desc（排序字段需通过selectOrderFldName指定，可为空）           -- 页码 
strWhere     varchar default ''  -- 查询条件(注意: 不要加where) 
)

as
 sql1 varchar(6000);--sql=>sql1
 ifallarchi int;
 SelectWhere1 varchar(100);
 TimeList1 varchar(200);
 strWhere1 varchar(1000);
 keyFldName      varchar(80);  -- 主键字段 
 TimeList2r varchar(200);
 selectarchinum1 varchar(100);
begin

 SelectWhere1:=SelectWhere;
 TimeList1 :=TimeList;
 strWhere1 :=strWhere;
 sql1:='';
 TimeList2r:=TimeList2;
 ifallarchi:=0; --默认建筑类别为所有类别
  selectarchinum1:=selectarchinum;
if not(SelectWhere1 is null or rtrim(SelectWhere1) = '' ) then
 SelectWhere1:=' and '||SelectWhere1;
end if;

if not(TimeList1 is null or rtrim(TimeList1) = '' ) then
 TimeList1:=' and '||TimeList1;
end if;

if not(TimeList2r is null or rtrim(TimeList2r) = '' ) then
 TimeList2r:=' and '||TimeList2r;
end if;

if not(selectarchinum1 is null or rtrim(selectarchinum1) = '' )
then
 selectarchinum1:=' and '||selectarchinum1;
 ifallarchi:=1; --选择了建筑类别
end if;

if ifallarchi=1 --如果选择了建筑类别


then
	if not(strWhere1 is null or rtrim(strWhere1) = '' ) then
	 strWhere1:=' where '||strWhere1;
         end if;

	if CryStyle='Month'--月报表
	then
	
	 sql1:='select '||strGetFields||' from (
	select  Energy_ID1,Energy_Subentry1,Energy_Subentry,T2.archinum,BSelectTime,BZGross,SSelectTime,SZGross,(BZGross-SZGross) as CGross,(str((BZGross-SZGross)/SZGross*100)||"%") as BFGross
        from(
        select T2.Energy_ID1,Energy_subentry1,Energy_Subentry,ArchiNum,BSelectTime,sum(BZGross) BZGross from
        (select a.AmMeter_ID,SubString(a.AmMeter_Num,14,2) as Energy_ID1,(select DictionaryValue_Value from DictionaryValue as b 
where SubString(a.AmMeter_Num,14,2)=b.DictionaryValue_Num and b.Dictionary_ID=8) Energy_Subentry1,(select DictionaryValue_Value from DictionaryValue as b where SubString(a.AmMeter_Num,14,1)=b.DictionaryValue_Num and b.Dictionary_ID=7) Energy_Subentry,(select DictionaryValue_Value from DictionaryValue as b where SubString(a.AmMeter_Num,8,1)=b.DictionaryValue_Num and b.Dictionary_ID=3) ArchiNum,SelectTime as BSelectTime,ZGross as BZGross 
        from AmMeter as a, T_MonthAm as b 
        where a.AmMeter_ID=b.AmMeter_ID '||TimeList1||SelectWhere1||selectarchinum1||' and substring(AmMeter_Num,14,1) in(''A'',''B'',''C'',''D''))T1 
        group by Energy_ID1,BSelectTime,Energy_Subentry,ArchiNum,Energy_subentry1)T2
        left join
        (select Energy_ID1,SSelectTime,sum(SZGross) SZGross from
        (select a.AmMeter_ID,SubString(a.AmMeter_Num,14,2) as Energy_ID1,SelectTime as SSelectTime,ZGross as SZGross 
        from AmMeter as a, T_MonthAm as b 
        where a.AmMeter_ID=b.AmMeter_ID '||TimeList2r||SelectWhere1||selectarchinum1||' and substring(AmMeter_Num,14,1) in(''A'',''B'',''C'',''D''))T1 
        group by Energy_ID1,SSelectTime)T3
        on T2.Energy_ID1=T3.Energy_ID1
        )T '||strWhere1;
--print sql1
	
	elsif CryStyle='Year'--年报表
	then

	 sql1:='select '||strGetFields||' from (
   	select  T2.Energy_ID1,Energy_Subentry1,Energy_Subentry,T2.archinum,BSelectTime,BZGross,SSelectTime,SZGross,(BZGross-SZGross) as CGross,(str((BZGross-SZGross)/SZGross*100)+"%") as BFGross
        from(
        select Energy_ID1,Energy_subentry1,Energy_Subentry,ArchiNum,BSelectTime,sum(BZGross) BZGross from
        (select a.AmMeter_ID,SubString(a.AmMeter_Num,14,2) as Energy_ID1,(select DictionaryValue_Value from DictionaryValue as b 
where SubString(a.AmMeter_Num,14,2)=b.DictionaryValue_Num and b.Dictionary_ID=8) Energy_Subentry1,(select DictionaryValue_Value from DictionaryValue as b where SubString(a.AmMeter_Num,14,1)=b.DictionaryValue_Num and b.Dictionary_ID=7) Energy_Subentry,(select DictionaryValue_Value from DictionaryValue as b where SubString(a.AmMeter_Num,8,1)=b.DictionaryValue_Num and b.Dictionary_ID=3) ArchiNum,substring(SelectTime,1,4) as BSelectTime,ZGross as BZGross 
        from AmMeter as a, T_MonthAm as b 
        where a.AmMeter_ID=b.AmMeter_ID '||TimeList1||SelectWhere1||selectarchinum1||' and substring(AmMeter_Num,14,1) in(''A'',''B'',''C'',''D''))T1 
        group by Energy_ID1,BSelectTime,Energy_Subentry,ArchiNum,Energy_subentry1)T2
        left join
        (select Energy_ID1,SSelectTime,sum(SZGross) SZGross from
        (select a.AmMeter_ID,SubString(a.AmMeter_Num,14,2) as Energy_ID1,substring(SelectTime,1,4) as SSelectTime,ZGross as SZGross 
        from AmMeter as a, T_MonthAm as b 
        where a.AmMeter_ID=b.AmMeter_ID '||TimeList2r||SelectWhere1||selectarchinum1||' and substring(AmMeter_Num,14,1) in(''A'',''B'',''C'',''D''))T1 
        group by Energy_ID1,SSelectTime)T3
        on T2.Energy_ID1=T3.Energy_ID1
        )T '||strWhere1;
	
	end if;

end if;

if ifallarchi=0 --如果没有选择建筑类别


then
	if not(strWhere1 is null or rtrim(strWhere1) = '' )  then
	 strWhere1:=' where '||strWhere1;
          end if;

	if CryStyle='Month'--月报表
	then
	
	 sql1:='select '||strGetFields||' from (
	select T2.Energy_ID1,Energy_Subentry1,Energy_Subentry,''所有类别'' as archinum,BSelectTime,BZGross,SSelectTime,SZGross,(BZGross-SZGross) as CGross,(str((BZGross-SZGross)/SZGross*100)+"%") as BFGross
        from(
        select Energy_ID1,Energy_Subentry,Energy_Subentry1,BSelectTime,sum(BZGross) BZGross from
        (select a.AmMeter_ID,SubString(a.AmMeter_Num,14,2) as Energy_ID1,(select DictionaryValue_Value from DictionaryValue as b 
where SubString(a.AmMeter_Num,14,2)=b.DictionaryValue_Num and b.Dictionary_ID=8) Energy_Subentry1,(select DictionaryValue_Value from DictionaryValue as b where SubString(a.AmMeter_Num,14,1)=b.DictionaryValue_Num and b.Dictionary_ID=7) Energy_Subentry,SelectTime as BSelectTime,ZGross as BZGross 
        from AmMeter as a, T_MonthAm as b 
        where a.AmMeter_ID=b.AmMeter_ID '||TimeList1||SelectWhere1||selectarchinum1||' and substring(AmMeter_Num,14,1) in(''A'',''B'',''C'',''D''))T1 
        group by Energy_ID1,BSelectTime,Energy_Subentry,Energy_Subentry1)T2
        left join
        (select Energy_ID1,SSelectTime,sum(SZGross) SZGross from
        (select a.AmMeter_ID,SubString(a.AmMeter_Num,14,2) as Energy_ID1,SelectTime as SSelectTime,ZGross as SZGross 
        from AmMeter as a, T_MonthAm as b 
        where a.AmMeter_ID=b.AmMeter_ID '||TimeList2r||SelectWhere1||selectarchinum1||' and substring(AmMeter_Num,14,1) in(''A'',''B'',''C'',''D''))T1 
        group by Energy_ID1,SSelectTime)T3
        on T2.Energy_ID1=T3.Energy_ID1
        )T '||strWhere1;
--print sql1
	
	elsif CryStyle='Year'--年报表
	then

	 sql1:='select '||strGetFields||' from (
	select T2.Energy_ID1,Energy_Subentry1,Energy_Subentry,''所有类别'' as archinum,BSelectTime,BZGross,SSelectTime,SZGross,(BZGross-SZGross) as CGross,(str((BZGross-SZGross)/SZGross*100)+"%") as BFGross
        from(
        select Energy_ID1,Energy_Subentry,Energy_Subentry1,BSelectTime,sum(BZGross) BZGross from
        (select a.AmMeter_ID,SubString(a.AmMeter_Num,14,2) as Energy_ID1,(select DictionaryValue_Value from DictionaryValue as b 
where SubString(a.AmMeter_Num,14,2)=b.DictionaryValue_Num and b.Dictionary_ID=8) Energy_Subentry1,(select DictionaryValue_Value from DictionaryValue as b where SubString(a.AmMeter_Num,14,1)=b.DictionaryValue_Num and b.Dictionary_ID=7) Energy_Subentry,substring(SelectTime,1,4) as BSelectTime,ZGross as BZGross 
        from AmMeter as a, T_MonthAm as b 
        where a.AmMeter_ID=b.AmMeter_ID '||TimeList1||SelectWhere1||selectarchinum1||' and substring(AmMeter_Num,14,1) in(''A'',''B'',''C'',''D''))T1 
        group by Energy_ID1,BSelectTime,Energy_Subentry,Energy_Subentry1)T2
        left join
        (select Energy_ID1,SSelectTime,sum(SZGross) SZGross from
        (select a.AmMeter_ID,SubString(a.AmMeter_Num,14,2) as Energy_ID1,substring(SelectTime,1,4) as SSelectTime,ZGross as SZGross 
        from AmMeter as a, T_MonthAm as b 
        where a.AmMeter_ID=b.AmMeter_ID '||TimeList2r||SelectWhere1||selectarchinum1||' and substring(AmMeter_Num,14,1) in(''A'',''B'',''C'',''D''))T1 
        group by Energy_ID1,SSelectTime)T3
        on T2.Energy_ID1=T3.Energy_ID1
        )T '||strWhere1;
	
	end if;

end if;


--print kind
--print sql1
execute immediate sql1;
end;










CREATE or replace  procedure D_EnergyAllSameTime
(
SelectWhere varchar default '',--搜索的条件
TimeList varchar default '',--搜索的条件
CryStyle varchar default 'Day',--报表统计类型
strGetFields varchar default '*', 
selectarchinum varchar, --建筑分类
orderFldDesc  varchar default '',--排序字段及排序方向，如addDate desc,id desc（排序字段需通过selectOrderFldName指定，可为空）           -- 页码 
strWhere     varchar default ''  -- 查询条件(注意: 不要加where) 
)

as
 sql1 varchar(6000);--sql=>sql1
 ifallarchi int;
 SelectWhere1 varchar(100);
 TimeList1 varchar(200);
 strWhere1 varchar(1000);
 keyFldName      varchar(80);  -- 主键字段 
 TimeList2r varchar(200);
 selectarchinum1 varchar(100);
begin

 SelectWhere1:=SelectWhere;
 TimeList1 :=TimeList;
 strWhere1 :=strWhere;
 sql1:='';
 ifallarchi:=0; --默认建筑类别为所有类别
 selectarchinum1:=selectarchinum;

if not(SelectWhere1 is null or rtrim(SelectWhere1) = '' ) then
 SelectWhere1:=' and '||SelectWhere1;
end if;

if not(TimeList1 is null or rtrim(TimeList1) = '' )  then
 TimeList1:=' and '||TimeList1;
end if;

if not(selectarchinum1 is null or rtrim(selectarchinum1) = '' )
then
 selectarchinum1:=' and '||selectarchinum1;
 ifallarchi:=1; --选择了建筑类别
end if;

if ifallarchi=1 --如果选择了建筑类别

then
	if not(strWhere1 is null or rtrim(strWhere1) = '' )  then
	  strWhere1:=' where '||strWhere1;
           end if;

	 if CryStyle='Month'--月报表
	then
	
	 sql1:='select '||strGetFields||' from (
          select Energy_ID,Energy_Subentry1,sum(ZGross) ZGross,sum(JGross) JGross,sum(FGross) FGross,sum(PGross) PGross,sum(GGross) GGross,Energy_Subentry,archinum,selecttime
  from(
  select a.AmMeter_ID,SubString(a.AmMeter_Num,14,2) as Energy_ID,(select DictionaryValue_Value from DictionaryValue as b 
where SubString(a.AmMeter_Num,14,2)=b.DictionaryValue_Num and b.Dictionary_ID=8) Energy_Subentry1,(select DictionaryValue_Value from DictionaryValue as b where SubString(a.AmMeter_Num,8,1)=b.DictionaryValue_Num and b.Dictionary_ID=3) ArchiNum,

ZGross,JGross,FGross,PGross,GGross,(select DictionaryValue_Value from DictionaryValue as b 
where SubString(a.AmMeter_Num,14,1)=b.DictionaryValue_Num and b.Dictionary_ID=7) Energy_Subentry,selecttime
  from AmMeter as a, T_MonthAm as b 
  where a.AmMeter_ID=b.AmMeter_ID '||TimeList1||SelectWhere1||selectarchinum1||' and substring(AmMeter_Num,14,1) in(''A'',''B'',''C'',''D'') 
)T1 
  group by Energy_Subentry1,Energy_ID,Energy_Subentry,selecttime,archinum 
        )T '||strWhere1;
	
	elsif CryStyle='Year'--年报表
	then

	 sql1:='select '||strGetFields||' from (
          select Energy_ID,Energy_Subentry1,sum(ZGross) ZGross,sum(JGross) JGross,sum(FGross) FGross,sum(PGross) PGross,sum(GGross) GGross,Energy_Subentry,archinum,selecttime
  from(
  select a.AmMeter_ID,SubString(a.AmMeter_Num,14,2) as Energy_ID,(select DictionaryValue_Value from DictionaryValue as b 
where SubString(a.AmMeter_Num,14,2)=b.DictionaryValue_Num and b.Dictionary_ID=8) Energy_Subentry1,(select DictionaryValue_Value from DictionaryValue as b where SubString(a.AmMeter_Num,8,1)=b.DictionaryValue_Num and b.Dictionary_ID=3) ArchiNum,

ZGross,JGross,FGross,PGross,GGross,(select DictionaryValue_Value from DictionaryValue as b 
where SubString(a.AmMeter_Num,14,1)=b.DictionaryValue_Num and b.Dictionary_ID=7) Energy_Subentry,substring(selecttime,1,4)selecttime
  from AmMeter as a, T_MonthAm as b 
  where a.AmMeter_ID=b.AmMeter_ID  '||TimeList1||SelectWhere1||selectarchinum1||' and substring(AmMeter_Num,14,1) in(''A'',''B'',''C'',''D'') 
)T1 
  group by Energy_Subentry1,Energy_ID,Energy_Subentry,selecttime,archinum 
        )T order by Energy_ID asc'||strWhere1;
	
	end if;



elsif ifallarchi=0 --如果没有选择建筑类别
then

	if not(strWhere1 is null or rtrim(strWhere1) = '' )  then
	 strWhere1:=' where '||strWhere1;
        end if;


     if CryStyle='Month'--月报表
	then
	
	 sql1:='select '||strGetFields||' from (
          select Energy_ID,Energy_Subentry1,sum(ZGross) ZGross,sum(JGross) JGross,sum(FGross) FGross,sum(PGross) PGross,sum(GGross) GGross,Energy_Subentry,''所有类别'' as archinum,selecttime
  from(
  select a.AmMeter_ID,SubString(a.AmMeter_Num,14,2) as Energy_ID,(select DictionaryValue_Value from DictionaryValue as b 
where SubString(a.AmMeter_Num,14,2)=b.DictionaryValue_Num and b.Dictionary_ID=8) Energy_Subentry1,

ZGross,JGross,FGross,PGross,GGross,(select DictionaryValue_Value from DictionaryValue as b 
where SubString(a.AmMeter_Num,14,1)=b.DictionaryValue_Num and b.Dictionary_ID=7) Energy_Subentry,selecttime
  from AmMeter as a, T_MonthAm as b 
  where a.AmMeter_ID=b.AmMeter_ID '||TimeList1||SelectWhere1||' and substring(AmMeter_Num,14,1) in(''A'',''B'',''C'',''D'') 
)T1 
  group by Energy_Subentry1,Energy_ID,Energy_Subentry,selecttime 
        )T '||strWhere1;
	
	elsif CryStyle='Year'--年报表
	then

	 sql1:='select '||strGetFields||' from (
          select Energy_ID,Energy_Subentry1,sum(ZGross) ZGross,sum(JGross) JGross,sum(FGross) FGross,sum(PGross) PGross,sum(GGross) GGross,Energy_Subentry,''所有类别'' as archinum,selecttime
  from(
  select a.AmMeter_ID,SubString(a.AmMeter_Num,14,2) as Energy_ID,(select DictionaryValue_Value from DictionaryValue as b 
where SubString(a.AmMeter_Num,14,2)=b.DictionaryValue_Num and b.Dictionary_ID=8) Energy_Subentry1,

ZGross,JGross,FGross,PGross,GGross,(select DictionaryValue_Value from DictionaryValue as b 
where SubString(a.AmMeter_Num,14,1)=b.DictionaryValue_Num and b.Dictionary_ID=7) Energy_Subentry,substring(selecttime,1,4)selecttime
  from AmMeter as a, T_MonthAm as b 
  where a.AmMeter_ID=b.AmMeter_ID  and substring(AmMeter_Num,14,1) in(''A'',''B'',''C'',''D'') 
)T1 
  group by Energy_Subentry1,Energy_ID,Energy_Subentry,selecttime 
        )T order by Energy_ID asc'||strWhere1;
	
	end if;


end if;

--print kind
--print sql
execute immediate sql1;
end;







CREATE or replace  procedure D_EnergyAllStatisics
(
SelectWhere varchar default '',--搜索的条件
TimeList varchar default '',--搜索的条件
CryStyle varchar default 'Day',--报表统计类型
strGetFields varchar default '*', 
selectarchinum varchar, --建筑分类
orderFldDesc  varchar default '',--排序字段及排序方向，如addDate desc,id desc（排序字段需通过selectOrderFldName指定，可为空）           -- 页码 
strWhere     varchar default ''  -- 查询条件(注意: 不要加where) 
)

as
 sql1 varchar(6000);--sql=>sql1
 ifallarchi int;
 SelectWhere1 varchar(100);
 TimeList1 varchar(200);
 strWhere1 varchar(1000);
 keyFldName      varchar(80);  -- 主键字段 
 selectarchinum1 varchar(100);
begin

 SelectWhere1:=SelectWhere;
 TimeList1 :=TimeList;
 strWhere1 :=strWhere;
 sql1:='';
 ifallarchi:=0; --默认建筑类别为所有类别

if not(SelectWhere1 is null or rtrim(SelectWhere1) = '' ) then
 SelectWhere1:=' and '||SelectWhere1;
 end if;

if not(TimeList1 is null or rtrim(TimeList1) = '' ) then
 TimeList1:=' and '||TimeList1;
end if;

if not(selectarchinum1 is null or rtrim(selectarchinum1) = '' )
then
 selectarchinum1:=' and '||selectarchinum1;
 ifallarchi:=1; --选择了建筑类别
end if;

if ifallarchi=1 --如果选择了建筑类别


then
	if not(strWhere1 is null or rtrim(strWhere1) = '' ) then
	 strWhere1:=' where '||strWhere1;
        end if;
	if CryStyle='Day'--日报表
	then
	
	 sql1:='select '||strGetFields||' from(
         select Energy_ID,Energy_Subentry1,ArchiNum,sum(ZGross) ZGross,sum(JGross) JGross,sum(FGross) FGross,sum(PGross) PGross,sum(GGross) GGross,Energy_Subentry
  from(
  select a.AmMeter_ID,SubString(a.AmMeter_Num,14,2) as Energy_ID,(select DictionaryValue_Value from DictionaryValue as b 
where SubString(a.AmMeter_Num,14,2)=b.DictionaryValue_Num and b.Dictionary_ID=8) Energy_Subentry1,
(select DictionaryValue_Value from DictionaryValue as b where SubString(a.AmMeter_Num,8,1)=b.DictionaryValue_Num and b.Dictionary_ID=3) ArchiNum,
ZGross,JGross,FGross,PGross,GGross,(select DictionaryValue_Value from DictionaryValue as b 
where SubString(a.AmMeter_Num,14,1)=b.DictionaryValue_Num and b.Dictionary_ID=7) Energy_Subentry
  from AmMeter as a, T_DayAm as b 
  where a.AmMeter_ID=b.AmMeter_ID  '||TimeList1||SelectWhere1||selectarchinum1||' and substring(AmMeter_Num,14,1) in(''A'',''B'',''C'',''D'')
)T1 
  group by Energy_Subentry1,Energy_ID,ArchiNum,Energy_Subentry
        )T order by Energy_ID asc '||strWhere1;

	
	elsif CryStyle='Month'--月报表
	then
	
	 sql1:='select '||strGetFields||' from (
         select Energy_ID,Energy_Subentry1,ArchiNum,sum(ZGross) ZGross,sum(JGross) JGross,sum(FGross) FGross,sum(PGross) PGross,sum(GGross) GGross,Energy_Subentry
  from(
  select a.AmMeter_ID,SubString(a.AmMeter_Num,14,2) as Energy_ID,(select DictionaryValue_Value from DictionaryValue as b 
where SubString(a.AmMeter_Num,14,2)=b.DictionaryValue_Num and b.Dictionary_ID=8) Energy_Subentry1,
(select DictionaryValue_Value from DictionaryValue as b where SubString(a.AmMeter_Num,8,1)=b.DictionaryValue_Num and b.Dictionary_ID=3) ArchiNum,
ZGross,JGross,FGross,PGross,GGross,(select DictionaryValue_Value from DictionaryValue as b 
where SubString(a.AmMeter_Num,14,1)=b.DictionaryValue_Num and b.Dictionary_ID=7) Energy_Subentry
  from AmMeter as a, T_MonthAm as b 
  where a.AmMeter_ID=b.AmMeter_ID  '||TimeList1||SelectWhere1||selectarchinum1||' and substring(AmMeter_Num,14,1) in(''A'',''B'',''C'',''D'')
)T1 
  group by Energy_Subentry1,Energy_ID,ArchiNum,Energy_Subentry
        )T order by Energy_ID asc'||strWhere1;
	
	elsif CryStyle='Year'--年报表
	then

	 sql1:='select '||strGetFields||' from (
	         select Energy_ID,Energy_Subentry1,ArchiNum,sum(ZGross) ZGross,sum(JGross) JGross,sum(FGross) FGross,sum(PGross) PGross,sum(GGross) GGross,Energy_Subentry
  from(
  select a.AmMeter_ID,SubString(a.AmMeter_Num,14,2) as Energy_ID,(select DictionaryValue_Value from DictionaryValue as b 
where SubString(a.AmMeter_Num,14,2)=b.DictionaryValue_Num and b.Dictionary_ID=8) Energy_Subentry1,
(select DictionaryValue_Value from DictionaryValue as b where SubString(a.AmMeter_Num,8,1)=b.DictionaryValue_Num and b.Dictionary_ID=3) ArchiNum,
ZGross,JGross,FGross,PGross,GGross,(select DictionaryValue_Value from DictionaryValue as b 
where SubString(a.AmMeter_Num,14,1)=b.DictionaryValue_Num and b.Dictionary_ID=7) Energy_Subentry
  from AmMeter as a, T_MonthAm as b 
  where a.AmMeter_ID=b.AmMeter_ID  '||TimeList1||SelectWhere1||selectarchinum1||' and substring(AmMeter_Num,14,1) in(''A'',''B'',''C'',''D'')
)T1 
  group by Energy_Subentry1,Energy_ID,ArchiNum,Energy_Subentry
        )T order by Energy_ID asc'||strWhere1;
	
	
	elsif CryStyle='Hour'--小时报表
	then
	 sql1:='select '||strGetFields||' from (
         select Energy_ID,Energy_Subentry1,ArchiNum,sum(ZGross) ZGross,sum(JGross) JGross,sum(FGross) FGross,sum(PGross) PGross,sum(GGross) GGross,Energy_Subentry
  from(
  select a.AmMeter_ID,SubString(a.AmMeter_Num,14,2) as Energy_ID,(select DictionaryValue_Value from DictionaryValue as b 
where SubString(a.AmMeter_Num,14,2)=b.DictionaryValue_Num and b.Dictionary_ID=8) Energy_Subentry1,
(select DictionaryValue_Value from DictionaryValue as b where SubString(a.AmMeter_Num,8,1)=b.DictionaryValue_Num and b.Dictionary_ID=3) ArchiNum,
ZGross,JGross,FGross,PGross,GGross,(select DictionaryValue_Value from DictionaryValue as b 
where SubString(a.AmMeter_Num,14,1)=b.DictionaryValue_Num and b.Dictionary_ID=7) Energy_Subentry
  from AmMeter as a, T_HourAm as b 
  where a.AmMeter_ID=b.AmMeter_ID  '||TimeList1||SelectWhere1||selectarchinum1||' and substring(AmMeter_Num,14,1) in(''A'',''B'',''C'',''D'')
)T1 
  group by Energy_Subentry1,Energy_ID,ArchiNum,Energy_Subentry
        )T order by Energy_ID asc'||strWhere1;
	end if;


elsif ifallarchi=0 --如果没有选择建筑类别
then

	if not(strWhere1 is null or rtrim(strWhere1) = '' ) then
	 strWhere1:=' where '||strWhere1;
         end if;

	if CryStyle='Day'--日报表
	then
	
	 sql1:='select '||strGetFields||' from(
          select Energy_ID,Energy_Subentry1,sum(ZGross) ZGross,sum(JGross) JGross,sum(FGross) FGross,sum(PGross) PGross,sum(GGross) GGross,Energy_Subentry,''所有类别'' as archinum
  from(
  select a.AmMeter_ID,SubString(a.AmMeter_Num,14,2) as Energy_ID,(select DictionaryValue_Value from DictionaryValue as b 
where SubString(a.AmMeter_Num,14,2)=b.DictionaryValue_Num and b.Dictionary_ID=8) Energy_Subentry1,

ZGross,JGross,FGross,PGross,GGross,(select DictionaryValue_Value from DictionaryValue as b 
where SubString(a.AmMeter_Num,14,1)=b.DictionaryValue_Num and b.Dictionary_ID=7) Energy_Subentry
  from AmMeter as a, T_DayAm as b 
  where a.AmMeter_ID=b.AmMeter_ID  '||TimeList1||SelectWhere1||'  and substring(AmMeter_Num,14,1) in(''A'',''B'',''C'',''D'')
)T1 
  group by Energy_Subentry1,Energy_ID,Energy_Subentry
        )T order by Energy_ID asc'||strWhere1;

	
	elsif CryStyle='Month'--月报表
	then
	
	 sql1:='select '||strGetFields||' from (
          select Energy_ID,Energy_Subentry1,sum(ZGross) ZGross,sum(JGross) JGross,sum(FGross) FGross,sum(PGross) PGross,sum(GGross) GGross,Energy_Subentry,''所有类别'' as archinum
  from(
  select a.AmMeter_ID,SubString(a.AmMeter_Num,14,2) as Energy_ID,(select DictionaryValue_Value from DictionaryValue as b 
where SubString(a.AmMeter_Num,14,2)=b.DictionaryValue_Num and b.Dictionary_ID=8) Energy_Subentry1,

ZGross,JGross,FGross,PGross,GGross,(select DictionaryValue_Value from DictionaryValue as b 
where SubString(a.AmMeter_Num,14,1)=b.DictionaryValue_Num and b.Dictionary_ID=7) Energy_Subentry
  from AmMeter as a, T_MonthAm as b 
  where a.AmMeter_ID=b.AmMeter_ID  '||TimeList1||SelectWhere1||'  and substring(AmMeter_Num,14,1) in(''A'',''B'',''C'',''D'')
)T1 
  group by Energy_Subentry1,Energy_ID,Energy_Subentry
        )T order by Energy_ID asc'||strWhere1;
	
	elsif CryStyle='Year'--年报表
	then

	 sql1:='select '||strGetFields||' from (
          select Energy_ID,Energy_Subentry1,sum(ZGross) ZGross,sum(JGross) JGross,sum(FGross) FGross,sum(PGross) PGross,sum(GGross) GGross,Energy_Subentry,''所有类别'' as archinum
  from(
  select a.AmMeter_ID,SubString(a.AmMeter_Num,14,2) as Energy_ID,(select DictionaryValue_Value from DictionaryValue as b 
where SubString(a.AmMeter_Num,14,2)=b.DictionaryValue_Num and b.Dictionary_ID=8) Energy_Subentry1,

ZGross,JGross,FGross,PGross,GGross,(select DictionaryValue_Value from DictionaryValue as b 
where SubString(a.AmMeter_Num,14,1)=b.DictionaryValue_Num and b.Dictionary_ID=7) Energy_Subentry
  from AmMeter as a, T_MonthAm as b 
  where a.AmMeter_ID=b.AmMeter_ID  '||TimeList1||SelectWhere1||'  and substring(AmMeter_Num,14,1) in(''A'',''B'',''C'',''D'')
)T1 
  group by Energy_Subentry1,Energy_ID,Energy_Subentry
        )T order by Energy_ID asc'||strWhere1;
	
	
	elsif CryStyle='Hour'--小时报表
	then
	 sql1:='select '||strGetFields||' from (
          select Energy_ID,Energy_Subentry1,sum(ZGross) ZGross,sum(JGross) JGross,sum(FGross) FGross,sum(PGross) PGross,sum(GGross) GGross,Energy_Subentry,''所有类别'' as archinum
  from(
  select a.AmMeter_ID,SubString(a.AmMeter_Num,14,2) as Energy_ID,(select DictionaryValue_Value from DictionaryValue as b 
where SubString(a.AmMeter_Num,14,2)=b.DictionaryValue_Num and b.Dictionary_ID=8) Energy_Subentry1,

ZGross,JGross,FGross,PGross,GGross,(select DictionaryValue_Value from DictionaryValue as b 
where SubString(a.AmMeter_Num,14,1)=b.DictionaryValue_Num and b.Dictionary_ID=7) Energy_Subentry
  from AmMeter as a, T_HourAm as b 
  where a.AmMeter_ID=b.AmMeter_ID  '||TimeList1||SelectWhere1||'  and substring(AmMeter_Num,14,1) in(''A'',''B'',''C'',''D'')
)T1 
  group by Energy_Subentry1,Energy_ID,Energy_Subentry
        )T order by Energy_ID asc'||strWhere1;
	end if;

end if;

--print kind
--print sql
execute immediate sql1;
end;






CREATE or replace procedure D_EnergyChainTime
(
SelectWhere varchar default '',--搜索的条件
TimeList varchar default '',--搜索日期条件本次
TimeList2 varchar default '',--搜索日期条件上次
CryStyle varchar default 'Day',--报表统计类型
strGetFields varchar default  '*', 
kind int default 0, --能耗分类,0为按分项能耗分类,1为按一级子项分类,2为按一级子项查询
kindvalue varchar, --能耗分类
selectarchinum varchar, --建筑分类
orderFldDesc  varchar default '',--排序字段及排序方向，如addDate desc,id desc（排序字段需通过selectOrderFldName指定，可为空）           -- 页码 
strWhere     varchar default ''  -- 查询条件(注意: 不要加where) 
)
as
 sql1 varchar(6000);
 ifallarchi int;
 SelectWhere1 varchar(100);
 TimeList1 varchar(200);
 TimeList2r varchar(200);
 strWhere1 varchar(1000);
 selectarchinum1 varchar(100);
begin

 SelectWhere1:=SelectWhere;
 TimeList1 :=TimeList;
 strWhere1 :=strWhere;
 TimeList2r:=TimeList2;
 selectarchinum1:=selectarchinum;
 sql1:='';
 ifallarchi:=0;--默认建筑类别为所有类别
if not(SelectWhere1 is null or rtrim(SelectWhere1) = '' ) then
 SelectWhere1:=' and '||SelectWhere1;
end if;

if not(TimeList1 is null or rtrim(TimeList1) = '' ) then
 TimeList1:=' and '||TimeList1;
end if;

if not(TimeList2r is null or rtrim(TimeList2r) = '' ) then
 TimeList2r:=' and '||TimeList2r;
end if;

if not(selectarchinum1 is null or rtrim(selectarchinum1) = '' )
then
 selectarchinum1:=' and '||selectarchinum1;
 ifallarchi:=1; --选择了建筑类别
end if;

if ifallarchi=1 --如果选择了建筑类别
then
if kind=0 --分项能耗
then
	if not(strWhere1 is null or rtrim(strWhere1) = '' ) then
	 strWhere1:=' where '||strWhere1;
         end if;

	if CryStyle='Month'--月报表
	then
	
	 sql1:='select '||strGetFields||' from (
        select T2.Energy_ID,(select DictionaryValue_Value from DictionaryValue as b where b.DictionaryValue_Num='''||substr(kindvalue,1,1)||''' and b.Dictionary_ID=7) Energy_Subentry,Energy_Subentry1,T2.archinum,BSelectTime,BZGross,SSelectTime,SZGross,(BZGross-SZGross) as CGross,(str((BZGross-SZGross)/SZGross*100)+"%") as BFGross
        from(
        select Energy_ID,Energy_Subentry1,ArchiNum,BSelectTime,sum(BZGross) BZGross from
        (select a.AmMeter_ID,SubString(a.AmMeter_Num,14,2) as Energy_ID,(select DictionaryValue_Value from DictionaryValue as b where substring(AmMeter_Num,14,2)=b.DictionaryValue_Num and b.Dictionary_ID=8) Energy_Subentry1,(select DictionaryValue_Value from DictionaryValue as b where SubString(a.AmMeter_Num,8,1)=b.DictionaryValue_Num and b.Dictionary_ID=3) ArchiNum,SelectTime as BSelectTime,ZGross as BZGross 
        from AmMeter as a, T_MonthAm as b 
        where a.AmMeter_ID=b.AmMeter_ID '||TimeList1||SelectWhere1||selectarchinum1||' and substr(AmMeter_Num,14,1)='''||substr(kindvalue,1,1)||''')T1 
        group by Energy_ID,BSelectTime,Energy_Subentry1,ArchiNum)T2
        left join
        (select Energy_ID,SSelectTime,sum(SZGross) SZGross from
        (select a.AmMeter_ID,SubString(a.AmMeter_Num,14,2) as Energy_ID,SelectTime as SSelectTime,ZGross as SZGross 
        from AmMeter as a, T_MonthAm as b 
        where a.AmMeter_ID=b.AmMeter_ID '||TimeList2r||SelectWhere1||selectarchinum1||' and substr(AmMeter_Num,14,1)='''||substr(kindvalue,1,1)||''')T1
        group by Energy_ID,SSelectTime)T3
        on T2.Energy_ID=T3.Energy_ID
        )T '||strWhere1;
--print sql1
	
	elsif CryStyle='Year'--年报表
	then

	 sql1:='select '||strGetFields||' from (
        select (T2.Energy_ID1+"0") as Energy_ID,Energy_Subentry,"所有类别" as energy_Subentry1,T2.archinum,BSelectTime,BZGross,SSelectTime,SZGross,(BZGross-SZGross) as CGross,(str((BZGross-SZGross)/SZGross*100)+"%") as BFGross
        from(
        select Energy_ID1,Energy_Subentry,ArchiNum,BSelectTime,sum(BZGross) BZGross from
        (select a.AmMeter_ID,SubString(a.AmMeter_Num,14,1) as Energy_ID1,(select DictionaryValue_Value from DictionaryValue as b where SubString(a.AmMeter_Num,14,1)=b.DictionaryValue_Num and b.Dictionary_ID=7) Energy_Subentry,(select DictionaryValue_Value from DictionaryValue as b where SubString(a.AmMeter_Num,8,1)=b.DictionaryValue_Num and b.Dictionary_ID=3) ArchiNum,substring(SelectTime,1,4) as BSelectTime,ZGross as BZGross 
        from AmMeter as a, T_MonthAm as b 
        where a.AmMeter_ID=b.AmMeter_ID '||TimeList1||SelectWhere1||selectarchinum1||')T1 
        group by Energy_ID1,BSelectTime,Energy_Subentry,ArchiNum)T2
        left join
        (select Energy_ID1,SSelectTime,sum(SZGross) SZGross from
        (select a.AmMeter_ID,SubString(a.AmMeter_Num,14,1) as Energy_ID1,substring(SelectTime,1,4) as SSelectTime,ZGross as SZGross 
        from AmMeter as a, T_MonthAm as b 
        where a.AmMeter_ID=b.AmMeter_ID '||TimeList2r||SelectWhere1||selectarchinum1||')T1 
        group by Energy_ID1,SSelectTime)T3
        on T2.Energy_ID1=T3.Energy_ID1
        )T '||strWhere1;
	
	end if;
elsif kind=1 --一级子项
then
    if not(strWhere1 is null or rtrim(strWhere1) = '' ) then
	 strWhere1:=' where '||strWhere1;
         end if;
	if CryStyle='Month'--月报表
	then
	
	sql1:='select '||strGetFields||' from (
        select T2.Energy_ID,(select DictionaryValue_Value from DictionaryValue as b where b.DictionaryValue_Num='''||substr(kindvalue,1,1)||''' and b.Dictionary_ID=7) Energy_Subentry,Energy_Subentry1,T2.archinum,BSelectTime,BZGross,SSelectTime,SZGross,(BZGross-SZGross) as CGross,(str((BZGross-SZGross)/SZGross*100)+"%") as BFGross
        from(
        select Energy_ID,Energy_Subentry1,ArchiNum,BSelectTime,sum(BZGross) BZGross from
        (select a.AmMeter_ID,SubString(a.AmMeter_Num,14,2) as Energy_ID,(select DictionaryValue_Value from DictionaryValue as b where substring(AmMeter_Num,14,2)=b.DictionaryValue_Num and b.Dictionary_ID=8) Energy_Subentry1,(select DictionaryValue_Value from DictionaryValue as b where SubString(a.AmMeter_Num,8,1)=b.DictionaryValue_Num and b.Dictionary_ID=3) ArchiNum,SelectTime as BSelectTime,ZGross as BZGross 
        from AmMeter as a, T_MonthAm as b 
        where a.AmMeter_ID=b.AmMeter_ID '||TimeList1||SelectWhere1||selectarchinum1||' and substr(AmMeter_Num,14,1)='''||substr(kindvalue,1,1)||''')T1 
        group by Energy_ID,BSelectTime,Energy_Subentry1,ArchiNum)T2
        left join
        (select Energy_ID,SSelectTime,sum(SZGross) SZGross from
        (select a.AmMeter_ID,SubString(a.AmMeter_Num,14,2) as Energy_ID,SelectTime as SSelectTime,ZGross as SZGross 
        from AmMeter as a, T_MonthAm as b 
        where a.AmMeter_ID=b.AmMeter_ID '||TimeList2r||SelectWhere1||selectarchinum1||' and substr(AmMeter_Num,14,1)='''||substr(kindvalue,1,1)||''')T1 
        group by Energy_ID,SSelectTime)T3
        on T2.Energy_ID=T3.Energy_ID
        )T '||strWhere1;
--print sql1	
	

        elsif CryStyle='Year'--年报表
	then

	 sql1:='select '||strGetFields||' from (
	select T2.Energy_ID,(select DictionaryValue_Value from DictionaryValue as b where b.DictionaryValue_Num='''||substr(kindvalue,1,1)||''' and b.Dictionary_ID=7) Energy_Subentry,Energy_Subentry1,T2.archinum,BSelectTime,BZGross,SSelectTime,SZGross,(BZGross-SZGross) as CGross,(str((BZGross-SZGross)/SZGross*100)+"%") as BFGross
        from(
        select Energy_ID,Energy_Subentry1,ArchiNum,BSelectTime,sum(BZGross) BZGross from
        (select a.AmMeter_ID,substr(a.AmMeter_Num,14,2) as Energy_ID,(select DictionaryValue_Value from DictionaryValue as b where substr(AmMeter_Num,14,2)=b.DictionaryValue_Num and b.Dictionary_ID=8) Energy_Subentry1,(select DictionaryValue_Value from DictionaryValue as b where substr(a.AmMeter_Num,8,1)=b.DictionaryValue_Num and b.Dictionary_ID=3) ArchiNum,substr(SelectTime,1,4) as BSelectTime,ZGross as BZGross 
        from AmMeter as a, T_MonthAm as b 
        where a.AmMeter_ID=b.AmMeter_ID '||TimeList1||SelectWhere1||selectarchinum1||' and substr(AmMeter_Num,14,1)='''||substr(kindvalue,1,1)||''')T1 
        group by Energy_ID,BSelectTime,Energy_Subentry1,ArchiNum)T2
        left join
        (select Energy_ID,SSelectTime,sum(SZGross) SZGross from
        (select a.AmMeter_ID,substr(a.AmMeter_Num,14,2) as Energy_ID,substr(SelectTime,1,4) as SSelectTime,ZGross as SZGross 
        from AmMeter as a, T_MonthAm as b 
        where a.AmMeter_ID=b.AmMeter_ID '||TimeList2r||SelectWhere1||selectarchinum1||' and substr(AmMeter_Num,14,1)='''||substr(kindvalue,1,1)||''')T1 
        group by Energy_ID,SSelectTime)T3
        on T2.Energy_ID=T3.Energy_ID
        )T '||strWhere1;
end if;
	
elsif kind=2 --按一级子项查询
then
	if not(strWhere1 is null or rtrim(strWhere1) = '' ) then
	 strWhere1:=' where '||strWhere1;
        end if;

	if CryStyle='Month'--月报表
	then
	
            sql1:='select '||strGetFields||' from (
	select T2.Energy_ID,(select DictionaryValue_Value from DictionaryValue as b where b.DictionaryValue_Num='''||substr(kindvalue,1,1)||''' and b.Dictionary_ID=7) Energy_Subentry,Energy_Subentry1,T2.archinum,BSelectTime,BZGross,SSelectTime,SZGross,(BZGross-SZGross) as CGross,(str((BZGross-SZGross)/SZGross*100)+"%") as BFGross
        from(
        select Energy_ID,Energy_Subentry1,ArchiNum,BSelectTime,sum(BZGross) BZGross from
        (select a.AmMeter_ID,substr(a.AmMeter_Num,14,2) as Energy_ID,(select DictionaryValue_Value from DictionaryValue as b where substr(AmMeter_Num,14,2)=b.DictionaryValue_Num and b.Dictionary_ID=8) Energy_Subentry1,(select DictionaryValue_Value from DictionaryValue as b where substr(a.AmMeter_Num,8,1)=b.DictionaryValue_Num and b.Dictionary_ID=3) ArchiNum,SelectTime as BSelectTime,ZGross as BZGross 
        from AmMeter as a, T_MonthAm as b 
        where a.AmMeter_ID=b.AmMeter_ID '||TimeList1||SelectWhere1||selectarchinum1||' and substr(AmMeter_Num,14,2)='''||kindvalue||''')T1 
        group by Energy_ID,BSelectTime,Energy_Subentry1,ArchiNum)T2
        left join
        (select Energy_ID,SSelectTime,sum(SZGross) SZGross from
        (select a.AmMeter_ID,substr(a.AmMeter_Num,14,2) as Energy_ID,SelectTime as SSelectTime,ZGross as SZGross 
        from AmMeter as a, T_MonthAm as b 
        where a.AmMeter_ID=b.AmMeter_ID '||TimeList2r||SelectWhere1||selectarchinum1||' and substr(AmMeter_Num,14,2)='''||kindvalue||''')T1 
        group by Energy_ID,SSelectTime)T3
        on T2.Energy_ID=T3.Energy_ID
        )T '||strWhere1;
	
	elsif CryStyle='Year'--年报表
	then

	 sql1:='select '||strGetFields||' from (
	select T2.Energy_ID,(select DictionaryValue_Value from DictionaryValue as b where b.DictionaryValue_Num='''||substr(kindvalue,1,1)||''' and b.Dictionary_ID=7) Energy_Subentry,Energy_Subentry1,T2.archinum,BSelectTime,BZGross,SSelectTime,SZGross,(BZGross-SZGross) as CGross,(str((BZGross-SZGross)/SZGross*100)+"%") as BFGross
        from(
        select Energy_ID,Energy_Subentry1,ArchiNum,BSelectTime,sum(BZGross) BZGross from
        (select a.AmMeter_ID,substr(a.AmMeter_Num,14,2) as Energy_ID,(select DictionaryValue_Value from DictionaryValue as b where substr(AmMeter_Num,14,2)=b.DictionaryValue_Num and b.Dictionary_ID=8) Energy_Subentry1,(select DictionaryValue_Value from DictionaryValue as b where substr(a.AmMeter_Num,8,1)=b.DictionaryValue_Num and b.Dictionary_ID=3) ArchiNum,substr(SelectTime,1,4) as BSelectTime,ZGross as BZGross 
        from AmMeter as a, T_MonthAm as b 
        where a.AmMeter_ID=b.AmMeter_ID '||TimeList1||SelectWhere1||selectarchinum1||' and substr(AmMeter_Num,14,2)='''||kindvalue||''')T1 
        group by Energy_ID,BSelectTime,Energy_Subentry1,ArchiNum)T2
        left join
        (select Energy_ID,SSelectTime,sum(SZGross) SZGross from
        (select a.AmMeter_ID,substr(a.AmMeter_Num,14,2) as Energy_ID,substr(SelectTime,1,4) as SSelectTime,ZGross as SZGross 
        from AmMeter as a, T_MonthAm as b 
        where a.AmMeter_ID=b.AmMeter_ID '||TimeList2r||SelectWhere1||selectarchinum1||' and substr(AmMeter_Num,14,2)='''||kindvalue||''')T1 
        group by Energy_ID,SSelectTime)T3
        on T2.Energy_ID=T3.Energy_ID
        )T '||strWhere1;
	
	end if;
end if;

elsif ifallarchi=0 --如果没有选择建筑类别
then
if kind=0 --分项能耗
then
	if not(strWhere1 is null or rtrim(strWhere1) = '' ) then
	 strWhere1:=' where '||strWhere1;
         end if;

	if CryStyle='Month'--月报表
	then
	
	 sql1:='select '||strGetFields||' from (
	select (T2.Energy_ID1+"0") as Energy_ID,Energy_Subentry,"所有类别" as energy_Subentry1,''所有类别'' as archinum,BSelectTime,BZGross,SSelectTime,SZGross,(BZGross-SZGross) as CGross,(str((BZGross-SZGross)/SZGross*100)+"%") as BFGross
        from(
        select Energy_ID1,Energy_Subentry,BSelectTime,sum(BZGross) BZGross from
        (select a.AmMeter_ID,substr(a.AmMeter_Num,14,1) as Energy_ID1,(select DictionaryValue_Value from DictionaryValue as b where substr(a.AmMeter_Num,14,1)=b.DictionaryValue_Num and b.Dictionary_ID=7) Energy_Subentry,SelectTime as BSelectTime,ZGross as BZGross 
        from AmMeter as a, T_MonthAm as b 
        where a.AmMeter_ID=b.AmMeter_ID '||TimeList1||SelectWhere1||selectarchinum1||')T1 
        group by Energy_ID1,BSelectTime,Energy_Subentry)T2
        left join
        (select Energy_ID1,SSelectTime,sum(SZGross) SZGross from
        (select a.AmMeter_ID,substr(a.AmMeter_Num,14,1) as Energy_ID1,SelectTime as SSelectTime,ZGross as SZGross 
        from AmMeter as a, T_MonthAm as b 
        where a.AmMeter_ID=b.AmMeter_ID '||TimeList2r||SelectWhere1||selectarchinum1||')T1 
        group by Energy_ID1,SSelectTime)T3
        on T2.Energy_ID1=T3.Energy_ID1
        )T '||strWhere1;
--print sql1
	
	elsif CryStyle='Year'--年报表
	then

	 sql1:='select '||strGetFields||' from (
        select (T2.Energy_ID1+"0") as Energy_ID,Energy_Subentry,"所有类别" as energy_Subentry1,''所有类别'' as archinum,BSelectTime,BZGross,SSelectTime,SZGross,(BZGross-SZGross) as CGross,(str((BZGross-SZGross)/SZGross*100)+"%") as BFGross
        from(
        select Energy_ID1,Energy_Subentry,BSelectTime,sum(BZGross) BZGross from
        (select a.AmMeter_ID,substr(a.AmMeter_Num,14,1) as Energy_ID1,(select DictionaryValue_Value from DictionaryValue as b where substr(a.AmMeter_Num,14,1)=b.DictionaryValue_Num and b.Dictionary_ID=7) Energy_Subentry,substr(SelectTime,1,4) as BSelectTime,ZGross as BZGross 
        from AmMeter as a, T_MonthAm as b 
        where a.AmMeter_ID=b.AmMeter_ID '||TimeList1||SelectWhere1||selectarchinum1||')T1 
        group by Energy_ID1,BSelectTime,Energy_Subentry)T2
        left join
        (select Energy_ID1,SSelectTime,sum(SZGross) SZGross from
        (select a.AmMeter_ID,substr(a.AmMeter_Num,14,1) as Energy_ID1,substr(SelectTime,1,4) as SSelectTime,ZGross as SZGross 
        from AmMeter as a, T_MonthAm as b 
        where a.AmMeter_ID=b.AmMeter_ID '||TimeList2r||SelectWhere1||selectarchinum1||')T1 
        group by Energy_ID1,SSelectTime)T3
        on T2.Energy_ID1=T3.Energy_ID1
        )T '||strWhere1;
	
	end if;

elsif kind=1 --一级子项
then
if not(strWhere1 is null or rtrim(strWhere1) = '' )  then
	 strWhere1:=' where '||strWhere1;
     end if;
	if CryStyle='Month'--月报表
	then
	
	 sql1:='select '||strGetFields||' from (
        select T2.Energy_ID,(select DictionaryValue_Value from DictionaryValue as b where b.DictionaryValue_Num='''||substr(kindvalue,1,1)||''' and b.Dictionary_ID=7) Energy_Subentry,Energy_Subentry1,''所有类别'' as archinum,BSelectTime,BZGross,SSelectTime,SZGross,(BZGross-SZGross) as CGross,(str((BZGross-SZGross)/SZGross*100)+"%") as BFGross
        from(
        select Energy_ID,Energy_Subentry1,BSelectTime,sum(BZGross) BZGross from
        (select a.AmMeter_ID,substr(a.AmMeter_Num,14,2) as Energy_ID,(select DictionaryValue_Value from DictionaryValue as b where substr(AmMeter_Num,14,2)=b.DictionaryValue_Num and b.Dictionary_ID=8) Energy_Subentry1,SelectTime as BSelectTime,ZGross as BZGross 
        from AmMeter as a, T_MonthAm as b 
        where a.AmMeter_ID=b.AmMeter_ID '||TimeList1||SelectWhere1||selectarchinum1||' and substr(AmMeter_Num,14,1)='''||substr(kindvalue,1,1)||''')T1 
        group by Energy_ID,BSelectTime,Energy_Subentry1)T2
        left join
        (select Energy_ID,SSelectTime,sum(SZGross) SZGross from
        (select a.AmMeter_ID,substr(a.AmMeter_Num,14,2) as Energy_ID,SelectTime as SSelectTime,ZGross as SZGross 
        from AmMeter as a, T_MonthAm as b 
        where a.AmMeter_ID=b.AmMeter_ID '||TimeList2r||SelectWhere1||selectarchinum1||' and substr(AmMeter_Num,14,1)='''||substr(kindvalue,1,1)||''')T1 
        group by Energy_ID,SSelectTime)T3
        on T2.Energy_ID=T3.Energy_ID
        )T '||strWhere1;
--print sql1
	
	elsif CryStyle='Year'--年报表
	then

	 sql1:='select '||strGetFields||' from (
	select T2.Energy_ID,(select DictionaryValue_Value from DictionaryValue as b where b.DictionaryValue_Num='''||substr(kindvalue,1,1)||''' and b.Dictionary_ID=7) Energy_Subentry,Energy_Subentry1,''所有类别'' as archinum,BSelectTime,BZGross,SSelectTime,SZGross,(BZGross-SZGross) as CGross,(str((BZGross-SZGross)/SZGross*100)+"%") as BFGross
        from(
        select Energy_ID,Energy_Subentry1,BSelectTime,sum(BZGross) BZGross from
        (select a.AmMeter_ID,substr(a.AmMeter_Num,14,2) as Energy_ID,(select DictionaryValue_Value from DictionaryValue as b where substr(AmMeter_Num,14,2)=b.DictionaryValue_Num and b.Dictionary_ID=8) Energy_Subentry1,substr(SelectTime,1,4) as BSelectTime,ZGross as BZGross 
        from AmMeter as a, T_MonthAm as b 
        where a.AmMeter_ID=b.AmMeter_ID '||TimeList1||SelectWhere1||selectarchinum1||' and substr(AmMeter_Num,14,1)='''||substr(kindvalue,1,1)||''')T1 
        group by Energy_ID,BSelectTime,Energy_Subentry1)T2
        left join
        (select Energy_ID,SSelectTime,sum(SZGross) SZGross from
        (select a.AmMeter_ID,substr(a.AmMeter_Num,14,2) as Energy_ID,substr(SelectTime,1,4) as SSelectTime,ZGross as SZGross 
        from AmMeter as a, T_MonthAm as b 
        where a.AmMeter_ID=b.AmMeter_ID '||TimeList2r||SelectWhere1||selectarchinum1||' and substr(AmMeter_Num,14,1)='''||substr(kindvalue,1,1)||''')T1 
        group by Energy_ID,SSelectTime)T3
        on T2.Energy_ID=T3.Energy_ID
        )T '||strWhere1;
	
	end if;
elsif kind=2 --按一级子项查询
then
	if not(strWhere1 is null or rtrim(strWhere1) = '' )
	then strWhere1:=' where '||strWhere1;
        end if;
        if CryStyle='Month'--月报表
	then
	
	 sql1:='select '||strGetFields||' from (
	select T2.Energy_ID,(select DictionaryValue_Value from DictionaryValue as b where b.DictionaryValue_Num='''||substr(kindvalue,1,1)||''' and b.Dictionary_ID=7) Energy_Subentry,Energy_Subentry1,''所有类别'' as archinum,BSelectTime,BZGross,SSelectTime,SZGross,(BZGross-SZGross) as CGross,(str((BZGross-SZGross)/SZGross*100)+"%") as BFGross
        from(
        select Energy_ID,Energy_Subentry1,BSelectTime,sum(BZGross) BZGross from
        (select a.AmMeter_ID,substr(a.AmMeter_Num,14,2) as Energy_ID,(select DictionaryValue_Value from DictionaryValue as b where substr(AmMeter_Num,14,2)=b.DictionaryValue_Num and b.Dictionary_ID=8) Energy_Subentry1,SelectTime as BSelectTime,ZGross as BZGross 
        from AmMeter as a, T_MonthAm as b 
        where a.AmMeter_ID=b.AmMeter_ID '||TimeList1||SelectWhere1||selectarchinum1||' and substr(AmMeter_Num,14,2)='''||kindvalue||''')T1 
        group by Energy_ID,BSelectTime,Energy_Subentry1)T2
        left join
        (select Energy_ID,SSelectTime,sum(SZGross) SZGross from
        (select a.AmMeter_ID,substr(a.AmMeter_Num,14,2) as Energy_ID,SelectTime as SSelectTime,ZGross as SZGross 
        from AmMeter as a, T_MonthAm as b 
        where a.AmMeter_ID=b.AmMeter_ID '||TimeList2r||SelectWhere1||selectarchinum1||' and substr(AmMeter_Num,14,2)='''||kindvalue||''')T1 
        group by Energy_ID,SSelectTime)T3
        on T2.Energy_ID=T3.Energy_ID
        )T '||strWhere1;
	
	elsif CryStyle='Year'--年报表
	then

	 sql1:='select '||strGetFields||' from (
	select T2.Energy_ID,(select DictionaryValue_Value from DictionaryValue as b where b.DictionaryValue_Num='''||substr(kindvalue,1,1)||''' and b.Dictionary_ID=7) Energy_Subentry,Energy_Subentry1,''所有类别'' as archinum,BSelectTime,BZGross,SSelectTime,SZGross,(BZGross-SZGross) as CGross,(str((BZGross-SZGross)/SZGross*100)+"%") as BFGross
        from(
        select Energy_ID,Energy_Subentry1,BSelectTime,sum(BZGross) BZGross from
        (select a.AmMeter_ID,substr(a.AmMeter_Num,14,2) as Energy_ID,(select DictionaryValue_Value from DictionaryValue as b where substr(AmMeter_Num,14,2)=b.DictionaryValue_Num and b.Dictionary_ID=8) Energy_Subentry1,substr(SelectTime,1,4) as BSelectTime,ZGross as BZGross 
        from AmMeter as a, T_MonthAm as b 
        where a.AmMeter_ID=b.AmMeter_ID '||TimeList1||SelectWhere1||selectarchinum1||' and substr(AmMeter_Num,14,2)='''||kindvalue||''')T1 
        group by Energy_ID,BSelectTime,Energy_Subentry1)T2
        left join
        (select Energy_ID,SSelectTime,sum(SZGross) SZGross from
        (select a.AmMeter_ID,substr(a.AmMeter_Num,14,2) as Energy_ID,substr(SelectTime,1,4) as SSelectTime,ZGross as SZGross 
        from AmMeter as a, T_MonthAm as b 
        where a.AmMeter_ID=b.AmMeter_ID '||TimeList2r||SelectWhere1||selectarchinum1||' and substr(AmMeter_Num,14,2)='''||kindvalue||''')T1 
        group by Energy_ID,SSelectTime)T3
        on T2.Energy_ID=T3.Energy_ID
        )T '||strWhere1;
	
	end if;

	
end if;
end if;
--print kind
--print sql1
execute immediate sql1;
end;









CREATE or replace procedure D_EnergyContrast
(
SelectWhere varchar default '',--搜索的条件
SelectWhere1 varchar default '',--汇总条件
TimeList varchar default '',--搜索的条件
strGetFields varchar default '*', 
orderFldDesc  varchar default '',--排序字段及排序方向，如addDate desc,id desc（排序字段需通过selectOrderFldName指定，可为空）           -- 页码 
strWhere     varchar default  ''  -- 查询条件(注意: 不要加where) 
)

as
 sql1 varchar(6000);--sql=》sql1
 SelectWherer varchar(200);
 SelectWhere1r varchar(200);
 TimeList1 varchar(200);
 strWhere1 varchar(1000);
begin

SelectWherer:=SelectWhere;
 TimeList1 :=TimeList;
 strWhere1:=strWhere;
SelectWhere1r:=SelectWhere1;
 sql1:='';
if not(SelectWherer is null or rtrim(SelectWherer) = '' ) then
 SelectWherer:=' and '||SelectWherer;
end if;

if not(SelectWhere1r is null or rtrim(SelectWhere1r) = '' ) then
 SelectWhere1r:=' and '||SelectWhere1r;
end if;

if not(TimeList1 is null or rtrim(TimeList1) = '' )  then
TimeList1:=' and '||TimeList1;
end if;

if not(strWhere1 is null or rtrim(strWhere1) = '' ) then
 strWhere1:=' where '||strWhere1;
	
 sql1:='select '||strGetFields||' from (
	select T3.*,(T3.GLZGross-T3.ZGross) as CGross
        from(
        select GL_ID,GLZGross,sum(ZGross) ZGross,T1.SelectTime
        from(
        select a.AmMeter_ID,SelectTime,ZGross
        from AmMeter as a, T_MonthAm as b
        where a.AmMeter_ID=b.AmMeter_ID '||TimeList1||SelectWherer||' )T1,
        ArchitectureGL as T2
        where T1.SelectTime=T2.GLTime '||SelectWhere1r||'  
        group by T1.SelectTime,GL_ID,GLZGross
        )T3
)T '||strWhere1;
end if;

execute immediate sql1;
--print sql
end;











CREATE or replace procedure D_EnergySameTime
(
SelectWhere varchar default '',--搜索的条件
TimeList varchar default '',--搜索的条件
CryStyle varchar default 'Day',--报表统计类型
strGetFields varchar default  '*', 
kind int default 0, --能耗分类,0为按分项能耗分类,1为按一级子项分类,2为按一级子项查询
kindvalue varchar, --能耗分类
selectarchinum varchar, --建筑分类
orderFldDesc  varchar default '',--排序字段及排序方向，如addDate desc,id desc（排序字段需通过selectOrderFldName指定，可为空）           -- 页码 
strWhere     varchar default ''  -- 查询条件(注意: 不要加where) 
)

as
 sql1 varchar(6000);
 ifallarchi int;
 SelectWhere1 varchar(100);
 TimeList1 varchar(200);
 strWhere1 varchar(1000);
 selectarchinum1 varchar(100);
begin

 SelectWhere1:=SelectWhere;
 TimeList1 :=TimeList;
 strWhere1 :=strWhere;
 selectarchinum1:=selectarchinum;
 sql1:='';
 ifallarchi:=0;--默认建筑类别为所有类别
if not(SelectWhere1 is null or rtrim(SelectWhere1) = '' ) then
 SelectWhere1:=' and '||SelectWhere1;
 end if;

if not(TimeList1 is null or rtrim(TimeList1) = '' ) then
 TimeList1:=' and '||TimeList1;
end if;

if not(selectarchinum1 is null or rtrim(selectarchinum1) = '' ) 
then
 selectarchinum1:=' and '||selectarchinum1;
 ifallarchi:=1;--选择了建筑类别
end if;



if ifallarchi=1 --如果选择了建筑类别
then
if kind=0 --分项能耗
then
	if not(strWhere1 is null or rtrim(strWhere1) = '' ) then
	 strWhere1:=' where '||strWhere1;
         end if;
	if CryStyle='Month'--月报表
	then
	
	 sql1:='select '||strGetFields||' from (
	select (T2.Energy_ID1+"0") as Energy_ID,T2.Energy_Subentry,T2.archinum,SelectTime,ZGross,JGross,FGross,PGross,GGross,''所有类别'' as energy_Subentry1
        from(
        select Energy_Subentry,Energy_ID1,ArchiNum,SelectTime,sum(ZGross) ZGross,sum(JGross) JGross,sum(FGross) FGross,sum(PGross) PGross,sum(GGross) GGross
        from(
        select a.AmMeter_ID,substr(a.AmMeter_Num,14,1) as Energy_ID1,(select DictionaryValue_Value from DictionaryValue as b where substr(a.AmMeter_Num,14,1)=b.DictionaryValue_Num and b.Dictionary_ID=7) Energy_Subentry,(select DictionaryValue_Value from DictionaryValue as b where substr(a.AmMeter_Num,8,1)=b.DictionaryValue_Num and b.Dictionary_ID=3) ArchiNum,SelectTime,ZGross,JGross,FGross,PGross,GGross 
        from AmMeter as a, T_MonthAm as b 
        where a.AmMeter_ID=b.AmMeter_ID '||TimeList1||SelectWhere1||selectarchinum1||' )T1 
        group by Energy_ID1,SelectTime,Energy_Subentry,ArchiNum) T2
        )T '||strWhere1;
--print sql1
	
	elsif CryStyle='Year'--年报表
	then

	 sql1:='select '||strGetFields||' from (
	select (T2.Energy_ID1+"0") as Energy_ID,T2.Energy_Subentry,T2.archinum,SelectTime,ZGross,JGross,FGross,PGross,GGross,''所有类别'' as energy_Subentry1
        from(
        select Energy_Subentry,Energy_ID1,ArchiNum,SelectTime,sum(ZGross) ZGross,sum(JGross) JGross,sum(FGross) FGross,sum(PGross) PGross,sum(GGross) GGross
        from(
        select a.AmMeter_ID,substr(a.AmMeter_Num,14,1) as Energy_ID1,(select DictionaryValue_Value from DictionaryValue as b where substr(a.AmMeter_Num,14,1)=b.DictionaryValue_Num and b.Dictionary_ID=7) Energy_Subentry,(select DictionaryValue_Value from DictionaryValue as b where substr(a.AmMeter_Num,8,1)=b.DictionaryValue_Num and b.Dictionary_ID=3) ArchiNum,substr(SelectTime,1,4) as SelectTime,ZGross,JGross,FGross,PGross,GGross 
        from AmMeter as a, T_MonthAm as b 
        where a.AmMeter_ID=b.AmMeter_ID '||TimeList1||SelectWhere1||selectarchinum1||' )T1 
        group by Energy_ID1,SelectTime,Energy_Subentry,ArchiNum) T2
        )T '||strWhere1;
	
	end if;

elsif kind=1 --一级子项
then
if not(strWhere1 is null or rtrim(strWhere1) = '' ) then
	 strWhere1:=' where '||strWhere1;
       end if;
	if CryStyle='Month'--月报表
	then
	
	 sql1:='select '||strGetFields||' from (
	select T2.*,(select DictionaryValue_Value from DictionaryValue as b where b.DictionaryValue_Num='''||substr(kindvalue,1,1)||''' and b.Dictionary_ID=7) Energy_Subentry
        from(
        select Energy_ID,Energy_Subentry1,ArchiNum,SelectTime,sum(ZGross) ZGross,sum(JGross) JGross,sum(FGross) FGross,sum(PGross) PGross,sum(GGross) GGross
        from(
        select a.AmMeter_ID,substr(a.AmMeter_Num,14,2) as Energy_ID,(select DictionaryValue_Value from DictionaryValue as b where substr(a.AmMeter_Num,14,2)=b.DictionaryValue_Num and b.Dictionary_ID=8) Energy_Subentry1,(select DictionaryValue_Value from DictionaryValue as b where substr(a.AmMeter_Num,8,1)=b.DictionaryValue_Num and b.Dictionary_ID=3) ArchiNum,SelectTime,ZGross,JGross,FGross,PGross,GGross 
        from AmMeter as a, T_MonthAm as b 
        where a.AmMeter_ID=b.AmMeter_ID '||TimeList1||SelectWhere1||selectarchinum1||' and substr(AmMeter_Num,14,1)='''||substr(kindvalue,1,1)||''')T1 
        group by Energy_ID,SelectTime,Energy_Subentry1,ArchiNum)T2
        )T '||strWhere1;
--print sql1
	
	elsif CryStyle='Year'--年报表
	then

	 sql1:='select '||strGetFields||' from (
	select T2.*,(select DictionaryValue_Value from DictionaryValue as b where b.DictionaryValue_Num='''||substr(kindvalue,1,1)||''' and b.Dictionary_ID=7) Energy_Subentry
        from(
        select Energy_ID,Energy_Subentry1,ArchiNum,SelectTime,sum(ZGross) ZGross,sum(JGross) JGross,sum(FGross) FGross,sum(PGross) PGross,sum(GGross) GGross
        from(
        select a.AmMeter_ID,substr(a.AmMeter_Num,14,2) as Energy_ID,(select DictionaryValue_Value from DictionaryValue as b where substr(a.AmMeter_Num,14,2)=b.DictionaryValue_Num and b.Dictionary_ID=8) Energy_Subentry1,(select DictionaryValue_Value from DictionaryValue as b where substr(a.AmMeter_Num,8,1)=b.DictionaryValue_Num and b.Dictionary_ID=3) ArchiNum,substr(SelectTime,1,4) as SelectTime,ZGross,JGross,FGross,PGross,GGross 
        from AmMeter as a, T_MonthAm as b 
        where a.AmMeter_ID=b.AmMeter_ID '||TimeList1||SelectWhere1||selectarchinum1||' and substr(AmMeter_Num,14,1)='''||substr(kindvalue,1,1)||''')T1 
        group by Energy_ID,SelectTime,Energy_Subentry1,ArchiNum)T2
        )T '||strWhere1;
	
	end if;

elsif kind=2 --按一级子项查询
then
	if not(strWhere1 is null or rtrim(strWhere1) = '' )  then
	 strWhere1:=' where '||strWhere1;
         end if;

	if CryStyle='Month'--月报表
	then
	
	 sql1:='select '||strGetFields||' from (
	select T2.*,(select DictionaryValue_Value from DictionaryValue as b where b.DictionaryValue_Num='''||substr(kindvalue,1,1)||''' and b.Dictionary_ID=7) Energy_Subentry
        from(
        select Energy_ID,Energy_Subentry1,ArchiNum,SelectTime,sum(ZGross) ZGross,sum(JGross) JGross,sum(FGross) FGross,sum(PGross) PGross,sum(GGross) GGross
        from(
        select a.AmMeter_ID,substr(a.AmMeter_Num,14,2) as Energy_ID,(select DictionaryValue_Value from DictionaryValue as b where substr(a.AmMeter_Num,14,2)=b.DictionaryValue_Num and b.Dictionary_ID=8) Energy_Subentry1,(select DictionaryValue_Value from DictionaryValue as b where substr(a.AmMeter_Num,8,1)=b.DictionaryValue_Num and b.Dictionary_ID=3) ArchiNum,SelectTime,ZGross,JGross,FGross,PGross,GGross 
        from AmMeter as a, T_MonthAm as b 
        where a.AmMeter_ID=b.AmMeter_ID '||TimeList1||SelectWhere1||selectarchinum1||' and substr(AmMeter_Num,14,2)='''||kindvalue||''')T1 
        group by Energy_ID,SelectTime,Energy_Subentry1,ArchiNum)T2
        )T '||strWhere1;
	
	elsif CryStyle='Year'--年报表
	then

	 sql1:='select '||strGetFields||' from (
	select T2.*,(select DictionaryValue_Value from DictionaryValue as b where b.DictionaryValue_Num='''||substr(kindvalue,1,1)||''' and b.Dictionary_ID=7) Energy_Subentry
        from(
        select Energy_ID,Energy_Subentry1,ArchiNum,SelectTime,sum(ZGross) ZGross,sum(JGross) JGross,sum(FGross) FGross,sum(PGross) PGross,sum(GGross) GGross
        from(
        select a.AmMeter_ID,substr(a.AmMeter_Num,14,2) as Energy_ID,(select DictionaryValue_Value from DictionaryValue as b where substr(a.AmMeter_Num,14,2)=b.DictionaryValue_Num and b.Dictionary_ID=8) Energy_Subentry1,(select DictionaryValue_Value from DictionaryValue as b where substr(a.AmMeter_Num,8,1)=b.DictionaryValue_Num and b.Dictionary_ID=3) ArchiNum,substr(SelectTime,1,4) as SelectTime,ZGross,JGross,FGross,PGross,GGross 
        from AmMeter as a, T_MonthAm as b 
        where a.AmMeter_ID=b.AmMeter_ID '||TimeList1||SelectWhere1||selectarchinum1||' and substr(AmMeter_Num,14,2)='''||kindvalue||''')T1 
        group by Energy_ID,SelectTime,Energy_Subentry1,ArchiNum)T2
        )T '||strWhere1;
	
	end if;
end if;


elsif ifallarchi=0 --如果没有选择建筑类别
then
if kind=0 --分项能耗
then
	if not(strWhere1 is null or rtrim(strWhere1) = '' ) then
	 strWhere1:=' where '||strWhere1;
         end if;

	if CryStyle='Month'--月报表
	then
	
	 sql1:='select '||strGetFields||' from (
	select (T2.Energy_ID1+"0") as Energy_ID,T2.Energy_Subentry,''所有类别'' as archinum,SelectTime,ZGross,JGross,FGross,PGross,GGross,''所有类别'' as energy_Subentry1
        from(
        select Energy_Subentry,Energy_ID1,SelectTime,sum(ZGross) ZGross,sum(JGross) JGross,sum(FGross) FGross,sum(PGross) PGross,sum(GGross) GGross
        from(
        select a.AmMeter_ID,substr(a.AmMeter_Num,14,1) as Energy_ID1,(select DictionaryValue_Value from DictionaryValue as b where substr(a.AmMeter_Num,14,1)=b.DictionaryValue_Num and b.Dictionary_ID=7) Energy_Subentry,SelectTime,ZGross,JGross,FGross,PGross,GGross 
        from AmMeter as a, T_MonthAm as b 
        where a.AmMeter_ID=b.AmMeter_ID '||TimeList1||SelectWhere1||selectarchinum1||' )T1 
        group by Energy_ID1,SelectTime,Energy_Subentry) T2
        )T '||strWhere1;
--print sql1
	
	elsif CryStyle='Year'--年报表
	then

	 sql1:='select '||strGetFields||' from (
	select (T2.Energy_ID1+"0") as Energy_ID,T2.Energy_Subentry,''所有类别'' as archinum,SelectTime,ZGross,JGross,FGross,PGross,GGross,''所有类别'' as energy_Subentry1
        from(
        select Energy_Subentry,Energy_ID1,SelectTime,sum(ZGross) ZGross,sum(JGross) JGross,sum(FGross) FGross,sum(PGross) PGross,sum(GGross) GGross
        from(
        select a.AmMeter_ID,substr(a.AmMeter_Num,14,1) as Energy_ID1,(select DictionaryValue_Value from DictionaryValue as b where substr(a.AmMeter_Num,14,1)=b.DictionaryValue_Num and b.Dictionary_ID=7) Energy_Subentry,substr(SelectTime,1,4) as SelectTime,ZGross,JGross,FGross,PGross,GGross 
        from AmMeter as a, T_MonthAm as b 
        where a.AmMeter_ID=b.AmMeter_ID '||TimeList1||SelectWhere1||selectarchinum1||' )T1 
        group by Energy_ID1,SelectTime,Energy_Subentry) T2
        )T '||strWhere1;
	
	end if;
elsif kind=1 --一级子项
then
if not(strWhere1 is null or rtrim(strWhere1) = '' ) then
	 strWhere1:=' where '||strWhere1;
 end if;
	if CryStyle='Month'--月报表
	then
	
	 sql1:='select '||strGetFields||' from (
	select T2.*,(select DictionaryValue_Value from DictionaryValue as b where b.DictionaryValue_Num='''||substr(kindvalue,1,1)||''' and b.Dictionary_ID=7) Energy_Subentry,''所有类别'' as archinum
        from(
        select Energy_ID,Energy_Subentry1,SelectTime,sum(ZGross) ZGross,sum(JGross) JGross,sum(FGross) FGross,sum(PGross) PGross,sum(GGross) GGross
        from(
        select a.AmMeter_ID,substr(a.AmMeter_Num,14,2) as Energy_ID,(select DictionaryValue_Value from DictionaryValue as b where substr(a.AmMeter_Num,14,2)=b.DictionaryValue_Num and b.Dictionary_ID=8) Energy_Subentry1,SelectTime,ZGross,JGross,FGross,PGross,GGross 
        from AmMeter as a, T_MonthAm as b 
        where a.AmMeter_ID=b.AmMeter_ID '||TimeList1||SelectWhere1||selectarchinum1||' and substr(AmMeter_Num,14,1)='''||substr(kindvalue,1,1)||''')T1 
        group by Energy_ID,SelectTime,Energy_Subentry1)T2
        )T '||strWhere1;
--print sql1
	
	elsif CryStyle='Year'--年报表
	then

	 sql1:='select '||strGetFields||' from (
	select T2.*,(select DictionaryValue_Value from DictionaryValue as b where b.DictionaryValue_Num='''||substr(kindvalue,1,1)||''' and b.Dictionary_ID=7) Energy_Subentry,''所有类别'' as archinum
        from(
        select Energy_ID,Energy_Subentry1,SelectTime,sum(ZGross) ZGross,sum(JGross) JGross,sum(FGross) FGross,sum(PGross) PGross,sum(GGross) GGross
        from(
        select a.AmMeter_ID,substr(a.AmMeter_Num,14,2) as Energy_ID,(select DictionaryValue_Value from DictionaryValue as b where substr(a.AmMeter_Num,14,2)=b.DictionaryValue_Num and b.Dictionary_ID=8) Energy_Subentry1,substr(SelectTime,1,4) as SelectTime,ZGross,JGross,FGross,PGross,GGross 
        from AmMeter as a, T_MonthAm as b 
        where a.AmMeter_ID=b.AmMeter_ID '||TimeList1||SelectWhere1||selectarchinum1||' and substr(AmMeter_Num,14,1)='''||substr(kindvalue,1,1)||''')T1 
        group by Energy_ID,SelectTime,Energy_Subentry1)T2
        )T '||strWhere1;
	
	end if;


elsif kind=2 --按一级子项查询
then
	if not(strWhere1 is null or rtrim(strWhere1) = '' )  then
	 strWhere1:=' where '||strWhere1;
        end if;

	if CryStyle='Month'--月报表
	then
	
	 sql1:='select '||strGetFields||' from (
	select T2.*,(select DictionaryValue_Value from DictionaryValue as b where b.DictionaryValue_Num='''||substr(kindvalue,1,1)||''' and b.Dictionary_ID=7) Energy_Subentry,''所有类别'' as archinum
        from(
        select Energy_ID,Energy_Subentry1,SelectTime,sum(ZGross) ZGross,sum(JGross) JGross,sum(FGross) FGross,sum(PGross) PGross,sum(GGross) GGross
        from(
        select a.AmMeter_ID,substr(a.AmMeter_Num,14,2) as Energy_ID,(select DictionaryValue_Value from DictionaryValue as b where substr(a.AmMeter_Num,14,2)=b.DictionaryValue_Num and b.Dictionary_ID=8) Energy_Subentry1,SelectTime,ZGross,JGross,FGross,PGross,GGross 
        from AmMeter as a, T_MonthAm as b 
        where a.AmMeter_ID=b.AmMeter_ID '||TimeList1||SelectWhere1||selectarchinum1||' and substr(AmMeter_Num,14,2)='''||kindvalue||''')T1 
        group by Energy_ID,SelectTime,Energy_Subentry1)T2
        )T '||strWhere1;
	
	elsif CryStyle='Year'--年报表
	then

	 sql1:='select '||strGetFields||' from (
	select T2.*,(select DictionaryValue_Value from DictionaryValue as b where b.DictionaryValue_Num='''||substr(kindvalue,1,1)||''' and b.Dictionary_ID=7) Energy_Subentry,''所有类别'' as archinum
        from(
        select Energy_ID,Energy_Subentry1,SelectTime,sum(ZGross) ZGross,sum(JGross) JGross,sum(FGross) FGross,sum(PGross) PGross,sum(GGross) GGross
        from(
        select a.AmMeter_ID,substr(a.AmMeter_Num,14,2) as Energy_ID,(select DictionaryValue_Value from DictionaryValue as b where substr(a.AmMeter_Num,14,2)=b.DictionaryValue_Num and b.Dictionary_ID=8) Energy_Subentry1,substr(SelectTime,1,4) as SelectTime,ZGross,JGross,FGross,PGross,GGross 
        from AmMeter as a, T_MonthAm as b 
        where a.AmMeter_ID=b.AmMeter_ID '||TimeList1||SelectWhere1||selectarchinum1||' and substr(AmMeter_Num,14,2)='''||kindvalue||''')T1 
        group by Energy_ID,SelectTime,Energy_Subentry1)T2
        )T '||strWhere1;
	
	end if;
end if;

end if;

--print kind
--print sql1
execute immediate sql1;
end;










CREATE or replace procedure D_EnergyStatisics
(
SelectWhere varchar default '',--搜索的条件
TimeList varchar default '',--搜索的条件
CryStyle varchar default 'Day',--报表统计类型
strGetFields varchar default  '*', 
kind int default 0, --能耗分类,0为按分项能耗分类,1为按一级子项分类,2为按一级子项查询
kindvalue varchar, --能耗分类
selectarchinum varchar, --建筑分类
orderFldDesc  varchar default '',--排序字段及排序方向，如addDate desc,id desc（排序字段需通过selectOrderFldName指定，可为空）           -- 页码 
strWhere     varchar default  ''  -- 查询条件(注意: 不要加where) 
)

as
 sql1 varchar(6000);
 ifallarchi int;
 SelectWhere1 varchar(100);
 TimeList1 varchar(200);
 strWhere1 varchar(1000);
 selectarchinum1 varchar(100);
begin

 SelectWhere1:=SelectWhere;
 TimeList1 :=TimeList;
 strWhere1 :=strWhere;
 selectarchinum1:=selectarchinum;
 sql1:='';
 ifallarchi:=0; --默认建筑类别为所有类别

if not(SelectWhere1 is null or rtrim(SelectWhere1) = '' ) then
 SelectWhere1:=' and '||SelectWhere1;
end if;

if not(TimeList1 is null or rtrim(TimeList1) = '' ) then
 TimeList1:=' and '||TimeList1;
end if;

if not(selectarchinum1 is null or rtrim(selectarchinum1) = '' ) 
then
 selectarchinum1:=' and '||selectarchinum1;
 ifallarchi:=1; --选择了建筑类别
end if;
if ifallarchi=1 --如果选择了建筑类别
then
if kind=0 --分项能耗
then
	if not(strWhere1 is null or rtrim(strWhere1) = '' ) then
	 strWhere1:=' where '||strWhere1;
         end if;

	if CryStyle='Day'--日报表
	then
	
	 sql1:='select '||strGetFields||' from(
        select (T2.Energy_ID1+"0") as Energy_ID,T2.Energy_Subentry,T2.archinum,ZGross,JGross,FGross,PGross,GGross,''所有类别'' as energy_Subentry1
        from(
        select Energy_Subentry,Energy_ID1,ArchiNum,sum(ZGross) ZGross,sum(JGross) JGross,sum(FGross) FGross,sum(PGross) PGross,sum(GGross) GGross
        from(
        select a.AmMeter_ID,substr(a.AmMeter_Num,14,1) as Energy_ID1,(select DictionaryValue_Value from DictionaryValue as b where substr(a.AmMeter_Num,14,1)=b.DictionaryValue_Num and b.Dictionary_ID=7) Energy_Subentry,(select DictionaryValue_Value from DictionaryValue as b where substr(a.AmMeter_Num,8,1)=b.DictionaryValue_Num and b.Dictionary_ID=3) ArchiNum,ZGross,JGross,FGross,PGross,GGross 
        from AmMeter as a, T_DayAm as b 
        where a.AmMeter_ID=b.AmMeter_ID '||TimeList1||SelectWhere1||selectarchinum1||')T1 
        group by Energy_Subentry,Energy_ID1,ArchiNum) T2
        )T '||strWhere1;

	
	elsif CryStyle='Month'--月报表
	then
	
	 sql1:='select '||strGetFields||' from (
	select (T2.Energy_ID1+"0") as Energy_ID,T2.Energy_Subentry,ZGross,JGross,FGross,PGross,GGross,''所有类别'' as energy_Subentry1
        from(
        select Energy_Subentry,Energy_ID1,ArchiNum,sum(ZGross) ZGross,sum(JGross) JGross,sum(FGross) FGross,sum(PGross) PGross,sum(GGross) GGross
        from(
        select a.AmMeter_ID,substr(a.AmMeter_Num,14,1) as Energy_ID1,(select DictionaryValue_Value from DictionaryValue as b where substr(a.AmMeter_Num,14,1)=b.DictionaryValue_Num and b.Dictionary_ID=7) Energy_Subentry,(select DictionaryValue_Value from DictionaryValue as b where substr(a.AmMeter_Num,8,1)=b.DictionaryValue_Num and b.Dictionary_ID=3) ArchiNum,ZGross,JGross,FGross,PGross,GGross 
        from AmMeter as a, T_MonthAm as b 
        where a.AmMeter_ID=b.AmMeter_ID '||TimeList1||SelectWhere1||selectarchinum1||' )T1 
        group by Energy_Subentry,Energy_ID1,ArchiNum) T2
        )T '||strWhere1;
	
	elsif CryStyle='Year'--年报表
	then

	 sql1:='select '||strGetFields||' from (
	select (T2.Energy_ID1+"0") as Energy_ID,T2.Energy_Subentry,ZGross,JGross,FGross,PGross,GGross,''所有类别'' as energy_Subentry1
        from(
        select Energy_Subentry,Energy_ID1,ArchiNum,sum(ZGross) ZGross,sum(JGross) JGross,sum(FGross) FGross,sum(PGross) PGross,sum(GGross) GGross
        from(
        select a.AmMeter_ID,substr(a.AmMeter_Num,14,1) as Energy_ID1,(select DictionaryValue_Value from DictionaryValue as b where substr(a.AmMeter_Num,14,1)=b.DictionaryValue_Num and b.Dictionary_ID=7) Energy_Subentry,(select DictionaryValue_Value from DictionaryValue as b where substr(a.AmMeter_Num,8,1)=b.DictionaryValue_Num and b.Dictionary_ID=3) ArchiNum,ZGross,JGross,FGross,PGross,GGross 
        from AmMeter as a, T_MonthAm as b 
        where a.AmMeter_ID=b.AmMeter_ID '||TimeList1||SelectWhere1||selectarchinum1||' )T1 
        group by Energy_Subentry,Energy_ID1,ArchiNum) T2
        )T '||strWhere1;
	
	
	elsif CryStyle='Hour'--小时报表
	then
	 sql1:='select '||strGetFields||' from (
	select (T2.Energy_ID1+"0") as Energy_ID,T2.Energy_Subentry,ZGross,JGross,FGross,PGross,GGross,''所有类别'' as energy_Subentry1
        from(
        select Energy_Subentry,Energy_ID1,ArchiNum,sum(ZGross) ZGross,sum(JGross) JGross,sum(FGross) FGross,sum(PGross) PGross,sum(GGross) GGross
        from(
        select a.AmMeter_ID,substr(a.AmMeter_Num,14,1) as Energy_ID1,(select DictionaryValue_Value from DictionaryValue as b where substr(a.AmMeter_Num,14,1)=b.DictionaryValue_Num and b.Dictionary_ID=7) Energy_Subentry,(select DictionaryValue_Value from DictionaryValue as b where substr(a.AmMeter_Num,8,1)=b.DictionaryValue_Num and b.Dictionary_ID=3) ArchiNum,ZGross,JGross,FGross,PGross,GGross 
        from AmMeter as a, T_HourAm as b 
        where a.AmMeter_ID=b.AmMeter_ID '||TimeList1||SelectWhere1||selectarchinum1||' )T1 
        group by Energy_Subentry,Energy_ID1,ArchiNum) T2
        )T '||strWhere1;
	end if;
elsif kind=1 --一级子项
then
if not(strWhere1 is null or rtrim(strWhere1) = '' ) then
	 strWhere1:=' where '||strWhere1;
 end if;
	if CryStyle='Day'--日报表
	then
	
	 sql1:='select '||strGetFields||' from(
        select T2.*,(select DictionaryValue_Value from DictionaryValue as b where b.DictionaryValue_Num='''||substr(kindvalue,1,1)||''' and b.Dictionary_ID=7) Energy_Subentry
        from(
        select Energy_ID,Energy_Subentry1,ArchiNum,sum(ZGross) ZGross,sum(JGross) JGross,sum(FGross) FGross,sum(PGross) PGross,sum(GGross) GGross
        from(
        select a.AmMeter_ID,substr(a.AmMeter_Num,14,2) as Energy_ID,(select DictionaryValue_Value from DictionaryValue as b where substr(a.AmMeter_Num,14,2)=b.DictionaryValue_Num and b.Dictionary_ID=8) Energy_Subentry1,(select DictionaryValue_Value from DictionaryValue as b where substr(a.AmMeter_Num,8,1)=b.DictionaryValue_Num and b.Dictionary_ID=3) ArchiNum,ZGross,JGross,FGross,PGross,GGross 
        from AmMeter as a, T_DayAm as b 
        where a.AmMeter_ID=b.AmMeter_ID '||TimeList1||SelectWhere1||selectarchinum1||' and substr(AmMeter_Num,14,1)='''||substr(kindvalue,1,1)||''')T1 
        group by Energy_Subentry1,Energy_ID,ArchiNum)T2
        )T '||strWhere1;
--print sql1
	
	elsif CryStyle='Month'--月报表
	then
	
	 sql1:='select '||strGetFields||' from (
	select T2.*,(select DictionaryValue_Value from DictionaryValue as b where b.DictionaryValue_Num='''||substr(kindvalue,1,1)||''' and b.Dictionary_ID=7) Energy_Subentry
        from(
        select Energy_ID,Energy_Subentry1,ArchiNum,sum(ZGross) ZGross,sum(JGross) JGross,sum(FGross) FGross,sum(PGross) PGross,sum(GGross) GGross
        from(
        select a.AmMeter_ID,substr(a.AmMeter_Num,14,2) as Energy_ID,(select DictionaryValue_Value from DictionaryValue as b where substr(a.AmMeter_Num,14,2)=b.DictionaryValue_Num and b.Dictionary_ID=8) Energy_Subentry1,(select DictionaryValue_Value from DictionaryValue as b where substr(a.AmMeter_Num,8,1)=b.DictionaryValue_Num and b.Dictionary_ID=3) ArchiNum,ZGross,JGross,FGross,PGross,GGross 
        from AmMeter as a, T_MonthAm as b 
        where a.AmMeter_ID=b.AmMeter_ID '||TimeList1||SelectWhere1||selectarchinum1||' and substr(AmMeter_Num,14,1)='''||substr(kindvalue,1,1)||''')T1 
        group by Energy_Subentry1,Energy_ID,ArchiNum)T2
        )T '||strWhere1;
	
	elsif CryStyle='Year'--年报表
	then

	 sql1:='select '||strGetFields||' from (
	select T2.*,(select DictionaryValue_Value from DictionaryValue as b where b.DictionaryValue_Num='''||substr(kindvalue,1,1)||''' and b.Dictionary_ID=7) Energy_Subentry
        from(
        select Energy_ID,Energy_Subentry1,ArchiNum,sum(ZGross) ZGross,sum(JGross) JGross,sum(FGross) FGross,sum(PGross) PGross,sum(GGross) GGross
        from(
        select a.AmMeter_ID,substr(a.AmMeter_Num,14,2) as Energy_ID,(select DictionaryValue_Value from DictionaryValue as b where substr(a.AmMeter_Num,14,2)=b.DictionaryValue_Num and b.Dictionary_ID=8) Energy_Subentry1,(select DictionaryValue_Value from DictionaryValue as b where substr(a.AmMeter_Num,8,1)=b.DictionaryValue_Num and b.Dictionary_ID=3) ArchiNum,ZGross,JGross,FGross,PGross,GGross 
        from AmMeter as a, T_MonthAm as b 
        where a.AmMeter_ID=b.AmMeter_ID '||TimeList1||SelectWhere1||selectarchinum1||' and substr(AmMeter_Num,14,1)='''||substr(kindvalue,1,1)||''')T1 
        group by Energy_Subentry1,Energy_ID,ArchiNum)T2
        )T '||strWhere1;
	
	
	elsif CryStyle='Hour'--小时报表
	then
	 sql1:='select '||strGetFields||' from (
	select T2.*,(select DictionaryValue_Value from DictionaryValue as b where b.DictionaryValue_Num='''||substr(kindvalue,1,1)||''' and b.Dictionary_ID=7) Energy_Subentry
        from(
        select Energy_ID,Energy_Subentry1,ArchiNum,sum(ZGross) ZGross,sum(JGross) JGross,sum(FGross) FGross,sum(PGross) PGross,sum(GGross) GGross
        from(
        select a.AmMeter_ID,substr(a.AmMeter_Num,14,2) as Energy_ID,(select DictionaryValue_Value from DictionaryValue as b where substr(a.AmMeter_Num,14,2)=b.DictionaryValue_Num and b.Dictionary_ID=8) Energy_Subentry1,(select DictionaryValue_Value from DictionaryValue as b where substr(a.AmMeter_Num,8,1)=b.DictionaryValue_Num and b.Dictionary_ID=3) ArchiNum,ZGross,JGross,FGross,PGross,GGross 
        from AmMeter as a, T_HourAm as b 
        where a.AmMeter_ID=b.AmMeter_ID '||TimeList1||SelectWhere1||selectarchinum1||' and substr(AmMeter_Num,14,1)='''||substr(kindvalue,1,1)||''')T1 
        group by Energy_Subentry1,Energy_ID,ArchiNum)T2
        )T '||strWhere1;
	end if;


elsif kind=2 --按一级子项查询
then
	if not(strWhere1 is null or rtrim(strWhere1) = '' ) then
	 strWhere1:=' where '||strWhere1;
          end if;

	if CryStyle='Day'--日报表
	then
	
	 sql1:='select '||strGetFields||' from(
        select T2.*,(select DictionaryValue_Value from DictionaryValue as b where b.DictionaryValue_Num='''||substr(kindvalue,1,1)||''' and b.Dictionary_ID=7) Energy_Subentry
        from(
        select Energy_ID,Energy_Subentry1,ArchiNum,sum(ZGross) ZGross,sum(JGross) JGross,sum(FGross) FGross,sum(PGross) PGross,sum(GGross) GGross
        from(
        select a.AmMeter_ID,substr(a.AmMeter_Num,14,2) as Energy_ID,(select DictionaryValue_Value from DictionaryValue as b where substr(a.AmMeter_Num,14,2)=b.DictionaryValue_Num and b.Dictionary_ID=8) Energy_Subentry1,(select DictionaryValue_Value from DictionaryValue as b where substr(a.AmMeter_Num,8,1)=b.DictionaryValue_Num and b.Dictionary_ID=3) ArchiNum,ZGross,JGross,FGross,PGross,GGross 
        from AmMeter as a, T_DayAm as b 
        where a.AmMeter_ID=b.AmMeter_ID '||TimeList1||SelectWhere1||selectarchinum1||' and substr(AmMeter_Num,14,2)='''||kindvalue||''')T1 
        group by Energy_Subentry1,Energy_ID,ArchiNum)T2
        )T '||strWhere1;

--print sql1
	



	elsif CryStyle='Month'--月报表
	then
	
	 sql1:='select '||strGetFields||' from (
	select T2.*,(select DictionaryValue_Value from DictionaryValue as b where b.DictionaryValue_Num='''||substr(kindvalue,1,1)||''' and b.Dictionary_ID=7) Energy_Subentry
        from(
        select Energy_ID,Energy_Subentry1,ArchiNum,sum(ZGross) ZGross,sum(JGross) JGross,sum(FGross) FGross,sum(PGross) PGross,sum(GGross) GGross
        from(
        select a.AmMeter_ID,substr(a.AmMeter_Num,14,2) as Energy_ID,(select DictionaryValue_Value from DictionaryValue as b where substr(a.AmMeter_Num,14,2)=b.DictionaryValue_Num and b.Dictionary_ID=8) Energy_Subentry1,(select DictionaryValue_Value from DictionaryValue as b where substr(a.AmMeter_Num,8,1)=b.DictionaryValue_Num and b.Dictionary_ID=3) ArchiNum,ZGross,JGross,FGross,PGross,GGross 
        from AmMeter as a, T_MonthAm as b 
        where a.AmMeter_ID=b.AmMeter_ID '||TimeList1||SelectWhere1||selectarchinum1||' and substr(AmMeter_Num,14,2)='''||kindvalue||''')T1 
        group by Energy_Subentry1,Energy_ID,ArchiNum)T2
        )T '||strWhere1;
	
	elsif CryStyle='Year'--年报表
	then

	sql1:='select '||strGetFields||' from (
	select T2.*,(select DictionaryValue_Value from DictionaryValue as b where b.DictionaryValue_Num='''||substr(kindvalue,1,1)||''' and b.Dictionary_ID=7) Energy_Subentry
        from(
        select Energy_ID,Energy_Subentry1,ArchiNum,sum(ZGross) ZGross,sum(JGross) JGross,sum(FGross) FGross,sum(PGross) PGross,sum(GGross) GGross
        from(
        select a.AmMeter_ID,substr(a.AmMeter_Num,14,2) as Energy_ID,(select DictionaryValue_Value from DictionaryValue as b where substr(a.AmMeter_Num,14,2)=b.DictionaryValue_Num and b.Dictionary_ID=8) Energy_Subentry1,(select DictionaryValue_Value from DictionaryValue as b where substr(a.AmMeter_Num,8,1)=b.DictionaryValue_Num and b.Dictionary_ID=3) ArchiNum,ZGross,JGross,FGross,PGross,GGross 
        from AmMeter as a, T_MonthAm as b 
        where a.AmMeter_ID=b.AmMeter_ID '||TimeList1||SelectWhere1||selectarchinum1||' and substr(AmMeter_Num,14,2)='''||kindvalue||''')T1 
        group by Energy_Subentry1,Energy_ID,ArchiNum)T2
        )T '||strWhere1;
	
	
	elsif CryStyle='Hour'--小时报表
	then
	 sql1:='select '||strGetFields||' from (
	select T2.*,(select DictionaryValue_Value from DictionaryValue as b where b.DictionaryValue_Num='''||substr(kindvalue,1,1)||''' and b.Dictionary_ID=7) Energy_Subentry
        from(
        select Energy_ID,Energy_Subentry1,ArchiNum,sum(ZGross) ZGross,sum(JGross) JGross,sum(FGross) FGross,sum(PGross) PGross,sum(GGross) GGross
        from(
        select a.AmMeter_ID,substr(a.AmMeter_Num,14,2) as Energy_ID,(select DictionaryValue_Value from DictionaryValue as b where substr(a.AmMeter_Num,14,2)=b.DictionaryValue_Num and b.Dictionary_ID=8) Energy_Subentry1,(select DictionaryValue_Value from DictionaryValue as b where substr(a.AmMeter_Num,8,1)=b.DictionaryValue_Num and b.Dictionary_ID=3) ArchiNum,ZGross,JGross,FGross,PGross,GGross 
        from AmMeter as a, T_HourAm as b 
        where a.AmMeter_ID=b.AmMeter_ID '||TimeList1||SelectWhere1||selectarchinum1||' and substr(AmMeter_Num,14,2)='''||kindvalue||''')T1 
        group by Energy_Subentry1,Energy_ID,ArchiNum)T2
        )T '||strWhere1;

	end if;
end if;
elsif ifallarchi=0 --如果没有选择建筑类别
then
if kind=0 --分项能耗
then
	if not(strWhere1 is null or rtrim(strWhere1) = '' ) then
	 strWhere1:=' where '||strWhere1;
        end if;

	if CryStyle='Day'--日报表
	then
	
	 sql1:='select '||strGetFields||' from(
        select (T2.Energy_ID1+"0") as Energy_ID,T2.Energy_Subentry,''所有类别'' as archinum,ZGross,JGross,FGross,PGross,GGross,''所有类别'' as energy_Subentry1
        from(
        select Energy_Subentry,Energy_ID1,sum(ZGross) ZGross,sum(JGross) JGross,sum(FGross) FGross,sum(PGross) PGross,sum(GGross) GGross
        from(
        select a.AmMeter_ID,substr(a.AmMeter_Num,14,1) as Energy_ID1,(select DictionaryValue_Value from DictionaryValue as b where substr(a.AmMeter_Num,14,1)=b.DictionaryValue_Num and b.Dictionary_ID=7) Energy_Subentry,ZGross,JGross,FGross,PGross,GGross 
        from AmMeter as a, T_DayAm as b 
        where a.AmMeter_ID=b.AmMeter_ID '||TimeList1||SelectWhere1||selectarchinum1||')T1 
        group by Energy_Subentry,Energy_ID1) T2
        )T '||strWhere1;

	
	elsif CryStyle='Month'--月报表
	then
	
	 sql1:='select '||strGetFields||' from (
	select (T2.Energy_ID1+"0") as Energy_ID,T2.Energy_Subentry,''所有类别'' as archinum,ZGross,JGross,FGross,PGross,GGross,''所有类别'' as energy_Subentry1
        from(
        select Energy_Subentry,Energy_ID1,sum(ZGross) ZGross,sum(JGross) JGross,sum(FGross) FGross,sum(PGross) PGross,sum(GGross) GGross
        from(
        select a.AmMeter_ID,substr(a.AmMeter_Num,14,1) as Energy_ID1,(select DictionaryValue_Value from DictionaryValue as b where substr(a.AmMeter_Num,14,1)=b.DictionaryValue_Num and b.Dictionary_ID=7) Energy_Subentry,ZGross,JGross,FGross,PGross,GGross 
        from AmMeter as a, T_MonthAm as b 
        where a.AmMeter_ID=b.AmMeter_ID '||TimeList1||SelectWhere1||selectarchinum1||' )T1 
        group by Energy_Subentry,Energy_ID1) T2
        )T '||strWhere1;
	
	elsif CryStyle='Year'--年报表
	then

	 sql1:='select '||strGetFields||' from (
	select (T2.Energy_ID1+"0") as Energy_ID,T2.Energy_Subentry,''所有类别'' as archinum,ZGross,JGross,FGross,PGross,GGross,''所有类别'' as energy_Subentry1
        from(
        select Energy_Subentry,Energy_ID1,sum(ZGross) ZGross,sum(JGross) JGross,sum(FGross) FGross,sum(PGross) PGross,sum(GGross) GGross
        from(
        select a.AmMeter_ID,substr(a.AmMeter_Num,14,1) as Energy_ID1,(select DictionaryValue_Value from DictionaryValue as b where substr(a.AmMeter_Num,14,1)=b.DictionaryValue_Num and b.Dictionary_ID=7) Energy_Subentry,ZGross,JGross,FGross,PGross,GGross 
        from AmMeter as a, T_MonthAm as b 
        where a.AmMeter_ID=b.AmMeter_ID '||TimeList1||SelectWhere1||selectarchinum1||' )T1 
        group by Energy_Subentry,Energy_ID1) T2
        )T '||strWhere1;
	
	
	elsif CryStyle='Hour'--小时报表
	then
	 sql1:='select '||strGetFields||' from (
	select (T2.Energy_ID1+"0") as Energy_ID,T2.Energy_Subentry,''所有类别'' as archinum,ZGross,JGross,FGross,PGross,GGross,''所有类别'' as energy_Subentry1
        from(
        select Energy_Subentry,Energy_ID1,sum(ZGross) ZGross,sum(JGross) JGross,sum(FGross) FGross,sum(PGross) PGross,sum(GGross) GGross
        from(
        select a.AmMeter_ID,substr(a.AmMeter_Num,14,1) as Energy_ID1,(select DictionaryValue_Value from DictionaryValue as b where substr(a.AmMeter_Num,14,1)=b.DictionaryValue_Num and b.Dictionary_ID=7) Energy_Subentry,ZGross,JGross,FGross,PGross,GGross 
        from AmMeter as a, T_HourAm as b 
        where a.AmMeter_ID=b.AmMeter_ID '||TimeList1||SelectWhere1||selectarchinum1||' )T1 
        group by Energy_Subentry,Energy_ID1) T2
        )T '||strWhere1;
	end if;

elsif kind=1 --一级子项
then
if not(strWhere1 is null or rtrim(strWhere1) = '' ) then
	 strWhere1:=' where '||strWhere1;
  end if;

	if CryStyle='Day'--日报表
	then
	
	 sql1:='select '||strGetFields||' from(
        select T2.*,(select DictionaryValue_Value from DictionaryValue as b where b.DictionaryValue_Num='''||substr(kindvalue,1,1)||''' and b.Dictionary_ID=7) Energy_Subentry,''所有类别'' as archinum
        from(
        select Energy_ID,Energy_Subentry1,sum(ZGross) ZGross,sum(JGross) JGross,sum(FGross) FGross,sum(PGross) PGross,sum(GGross) GGross
        from(
        select a.AmMeter_ID,substr(a.AmMeter_Num,14,2) as Energy_ID,(select DictionaryValue_Value from DictionaryValue as b where substr(a.AmMeter_Num,14,2)=b.DictionaryValue_Num and b.Dictionary_ID=8) Energy_Subentry1,ZGross,JGross,FGross,PGross,GGross 
        from AmMeter as a, T_DayAm as b 
        where a.AmMeter_ID=b.AmMeter_ID '||TimeList1||SelectWhere1||selectarchinum1||' and substr(AmMeter_Num,14,1)='''||substr(kindvalue,1,1)||''')T1 
        group by Energy_Subentry1,Energy_ID)T2
        )T '||strWhere1;
--print sql1
	
	elsif CryStyle='Month'--月报表
	then
	
	 sql1:='select '||strGetFields||' from (
	select T2.*,(select DictionaryValue_Value from DictionaryValue as b where b.DictionaryValue_Num='''||substr(kindvalue,1,1)||''' and b.Dictionary_ID=7) Energy_Subentry,''所有类别'' as archinum
        from(
        select Energy_ID,Energy_Subentry1,sum(ZGross) ZGross,sum(JGross) JGross,sum(FGross) FGross,sum(PGross) PGross,sum(GGross) GGross
        from(
        select a.AmMeter_ID,substr(a.AmMeter_Num,14,2) as Energy_ID,(select DictionaryValue_Value from DictionaryValue as b where substr(a.AmMeter_Num,14,2)=b.DictionaryValue_Num and b.Dictionary_ID=8) Energy_Subentry1,ZGross,JGross,FGross,PGross,GGross 
        from AmMeter as a, T_MonthAm as b 
        where a.AmMeter_ID=b.AmMeter_ID '||TimeList1||SelectWhere1||selectarchinum1||' and substr(AmMeter_Num,14,1)='''||substr(kindvalue,1,1)||''')T1 
        group by Energy_Subentry1,Energy_ID)T2
        )T '||strWhere1;
	
	elsif CryStyle='Year'--年报表
	then

	 sql1:='select '||strGetFields||' from (
	select T2.*,(select DictionaryValue_Value from DictionaryValue as b where b.DictionaryValue_Num='''||substr(kindvalue,1,1)||''' and b.Dictionary_ID=7) Energy_Subentry,''所有类别'' as archinum
        from(
        select Energy_ID,Energy_Subentry1,sum(ZGross) ZGross,sum(JGross) JGross,sum(FGross) FGross,sum(PGross) PGross,sum(GGross) GGross
        from(
        select a.AmMeter_ID,substr(a.AmMeter_Num,14,2) as Energy_ID,(select DictionaryValue_Value from DictionaryValue as b where substr(a.AmMeter_Num,14,2)=b.DictionaryValue_Num and b.Dictionary_ID=8) Energy_Subentry1,ZGross,JGross,FGross,PGross,GGross 
        from AmMeter as a, T_MonthAm as b 
        where a.AmMeter_ID=b.AmMeter_ID '||TimeList1||SelectWhere1||selectarchinum1||' and substr(AmMeter_Num,14,1)='''||substr(kindvalue,1,1)||''')T1 
        group by Energy_Subentry1,Energy_ID)T2
        )T '||strWhere1;
	
	
	elsif CryStyle='Hour'--小时报表
	then
	 sql1:='select '||strGetFields||' from (
	select T2.*,(select DictionaryValue_Value from DictionaryValue as b where b.DictionaryValue_Num='''||substr(kindvalue,1,1)||''' and b.Dictionary_ID=7) Energy_Subentry,''所有类别'' as archinum
        from(
        select Energy_ID,Energy_Subentry1,sum(ZGross) ZGross,sum(JGross) JGross,sum(FGross) FGross,sum(PGross) PGross,sum(GGross) GGross
        from(
        select a.AmMeter_ID,substr(a.AmMeter_Num,14,2) as Energy_ID,(select DictionaryValue_Value from DictionaryValue as b where substr(a.AmMeter_Num,14,2)=b.DictionaryValue_Num and b.Dictionary_ID=8) Energy_Subentry1,ZGross,JGross,FGross,PGross,GGross 
        from AmMeter as a, T_HourAm as b 
        where a.AmMeter_ID=b.AmMeter_ID '||TimeList1||SelectWhere1||selectarchinum1||' and substr(AmMeter_Num,14,1)='''||substr(kindvalue,1,1)||''')T1 
        group by Energy_Subentry1,Energy_ID)T2
        )T '||strWhere1;
	end if;


elsif kind=2 --按一级子项查询
then
	if not(strWhere1 is null or rtrim(strWhere1) = '' )  then
	 strWhere1:=' where '||strWhere1;
        end if;

	if CryStyle='Day'--日报表
	then
	
	 sql1:='select '||strGetFields||' from(
        select T2.*,(select DictionaryValue_Value from DictionaryValue as b where b.DictionaryValue_Num='''||substr(kindvalue,1,1)||''' and b.Dictionary_ID=7) Energy_Subentry,''所有类别'' as archinum
        from(
        select Energy_ID,Energy_Subentry1,sum(ZGross) ZGross,sum(JGross) JGross,sum(FGross) FGross,sum(PGross) PGross,sum(GGross) GGross
        from(
        select a.AmMeter_ID,substr(a.AmMeter_Num,14,2) as Energy_ID,(select DictionaryValue_Value from DictionaryValue as b where substr(a.AmMeter_Num,14,2)=b.DictionaryValue_Num and b.Dictionary_ID=8) Energy_Subentry1,ZGross,JGross,FGross,PGross,GGross 
        from AmMeter as a, T_DayAm as b 
        where a.AmMeter_ID=b.AmMeter_ID '||TimeList1||SelectWhere1||selectarchinum1||' and substr(AmMeter_Num,14,2)='''||kindvalue||''')T1 
        group by Energy_Subentry1,Energy_ID)T2
        )T '||strWhere1;

--print sql1
	



	elsif CryStyle='Month'--月报表
	then
	
	 sql1:='select '||strGetFields||' from (
	select T2.*,(select DictionaryValue_Value from DictionaryValue as b where b.DictionaryValue_Num='''||substr(kindvalue,1,1)||''' and b.Dictionary_ID=7) Energy_Subentry,''所有类别'' as archinum
        from(
        select Energy_ID,Energy_Subentry1,sum(ZGross) ZGross,sum(JGross) JGross,sum(FGross) FGross,sum(PGross) PGross,sum(GGross) GGross
        from(
        select a.AmMeter_ID,substr(a.AmMeter_Num,14,2) as Energy_ID,(select DictionaryValue_Value from DictionaryValue as b where substr(a.AmMeter_Num,14,2)=b.DictionaryValue_Num and b.Dictionary_ID=8) Energy_Subentry1,ZGross,JGross,FGross,PGross,GGross 
        from AmMeter as a, T_MonthAm as b 
        where a.AmMeter_ID=b.AmMeter_ID '||TimeList1||SelectWhere1||selectarchinum1||' and substr(AmMeter_Num,14,2)='''||kindvalue||''')T1 
        group by Energy_Subentry1,Energy_ID)T2
        )T '||strWhere1;
	
	elsif CryStyle='Year'--年报表
	then

	 sql1:='select '||strGetFields||' from (
	select T2.*,(select DictionaryValue_Value from DictionaryValue as b where b.DictionaryValue_Num='''||substr(kindvalue,1,1)||''' and b.Dictionary_ID=7) Energy_Subentry,''所有类别'' as archinum
        from(
        select Energy_ID,Energy_Subentry1,sum(ZGross) ZGross,sum(JGross) JGross,sum(FGross) FGross,sum(PGross) PGross,sum(GGross) GGross
        from(
        select a.AmMeter_ID,substr(a.AmMeter_Num,14,2) as Energy_ID,(select DictionaryValue_Value from DictionaryValue as b where substr(a.AmMeter_Num,14,2)=b.DictionaryValue_Num and b.Dictionary_ID=8) Energy_Subentry1,ZGross,JGross,FGross,PGross,GGross 
        from AmMeter as a, T_MonthAm as b 
        where a.AmMeter_ID=b.AmMeter_ID '||TimeList1||SelectWhere1||selectarchinum1||' and substr(AmMeter_Num,14,2)='''||kindvalue||''')T1 
        group by Energy_Subentry1,Energy_ID)T2
        )T '||strWhere1;
	
	
	elsif CryStyle='Hour'--小时报表
	then
	 sql1:='select '||strGetFields||' from (
	select T2.*,(select DictionaryValue_Value from DictionaryValue as b where b.DictionaryValue_Num='''||substr(kindvalue,1,1)||''' and b.Dictionary_ID=7) Energy_Subentry,''所有类别'' as archinum
        from(
        select Energy_ID,Energy_Subentry1,sum(ZGross) ZGross,sum(JGross) JGross,sum(FGross) FGross,sum(PGross) PGross,sum(GGross) GGross
        from(
        select a.AmMeter_ID,substr(a.AmMeter_Num,14,2) as Energy_ID,(select DictionaryValue_Value from DictionaryValue as b where substr(a.AmMeter_Num,14,2)=b.DictionaryValue_Num and b.Dictionary_ID=8) Energy_Subentry1,ZGross,JGross,FGross,PGross,GGross 
        from AmMeter as a, T_HourAm as b 
        where a.AmMeter_ID=b.AmMeter_ID '||TimeList1||SelectWhere1||selectarchinum1||' and substr(AmMeter_Num,14,2)='''||kindvalue||''')T1 
        group by Energy_Subentry1,Energy_ID)T2
        )T '||strWhere1;

	end if;

end if;
end if;
--print kind
--print sql1
execute immediate sql1;
end;











CREATE or replace procedure D_PartmentChainTime
(
SelectWhere varchar default '',--搜索的条件
TimeList varchar default '',--搜索的条件
TimeList2 varchar default '',--搜索的条件
CryStyle varchar default 'Month',--报表统计类型
strGetFields varchar default  '*', 
orderFldDesc  varchar default '',--排序字段及排序方向，如addDate desc,id desc（排序字段需通过selectOrderFldName指定，可为空） 
strWhere     varchar default ''  -- 查询条件(注意: 不要加where) 
)
as
 sql1 varchar(6000);--sql=》sql1
 SelectWhere1 varchar(100);
 TimeListr varchar(200);
 TimeList2r varchar(200);
 strWhere1 varchar(1000); 
begin

 SelectWhere1:=SelectWhere;
 TimeListr :=TimeList;
 strWhere1 :=strWhere;
 sql1:='';
 TimeList2r:=TimeList2;
if not(SelectWhere1 is null or rtrim(SelectWhere1) = '' ) then
 SelectWhere1:=' where '||SelectWhere1;
end if;

if not(TimeListr is null or rtrim(TimeListr) = '' ) then
 TimeListr:=' where '||TimeListr;
end if;

if not(TimeList2r is null or rtrim(TimeList2r) = '' ) then
 TimeList2r:=' where '||TimeList2r;
end if;

	if not(strWhere1 is null or rtrim(strWhere1) = '' ) then
	 strWhere1:=' where '||strWhere1;
         end if;

	
	if CryStyle='Month'--月报表
	then
	

	 sql1:='select '||strGetFields||' from (
select Partment_Num,Partment_Name,AmMeterCount,BZGross,SZGross,(BZGross-SZGross)CGross,cast(cast((BZGross-SZGross)/SZGross*100 as numeric(12,3)) as varchar(20))||''%''  BFGross from 
(select Partment,sum(BZGross) BZGross,sum(SZGross) SZGross,Count(AmMeter_ID)AmMeterCount  from
(select T1.AmMeter_ID,T1.ZGross BZGross,T2.ZGross SZGross,(select Partment from AmMeter as a where a.AmMeter_ID=T1.AmMeter_ID)Partment from 
(select AmMeter_ID,ZGross from T_MonthAm  '||TimeListr||')T1 inner join 
(select AmMeter_ID,ZGross from T_MonthAm '||TimeList2r||')T2 on T1.AmMeter_ID=T2.AmMeter_ID)T3  group by Partment)T4
left join (select DictionaryValue_ID as Partment,DictionaryValue_Num as Partment_Num,DictionaryValue_Value as Partment_Name from DictionaryValue where Dictionary_ID=1)T5
on T4.Partment=T5.Partment
)T '||strWhere1;


	
	elsif CryStyle='Year'--年报表
	then

	
	 sql1:='select '||strGetFields||' from (
select Partment_Num,Partment_Name,AmMeterCount,BZGross,SZGross,(BZGross-SZGross)CGross,cast(cast((BZGross-SZGross)/SZGross*100 as numeric(12,3)) as varchar(20))||''%''  BFGross from 
(select Partment,sum(BZGross) BZGross,sum(SZGross) SZGross,Count(AmMeter_ID)AmMeterCount  from
(select T1.AmMeter_ID,T1.ZGross BZGross,T2.ZGross SZGross,(select Partment from AmMeter as a where a.AmMeter_ID=T1.AmMeter_ID)Partment from 
(select AmMeter_ID,ZGross from T_MonthAm  '||TimeListr||')T1 inner join 
(select AmMeter_ID,ZGross from T_MonthAm '||TimeList2r||')T2 on T1.AmMeter_ID=T2.AmMeter_ID)T3  group by Partment)T4
left join (select DictionaryValue_ID as Partment,DictionaryValue_Num as Partment_Num,DictionaryValue_Value as Partment_Name from DictionaryValue where Dictionary_ID=1)T5
on T4.Partment=T5.Partment
)T '||strWhere1;


	end if;
	
		execute immediate sql1;
end;











CREATE or replace procedure D_PartmentSameTime
(
SelectWhere varchar default '',--搜索的条件
TimeList varchar default '',--搜索的条件
CryStyle varchar default 'Month',--报表统计类型
strGetFields varchar default  '*', 
orderFldDesc  varchar default 'Partment_Num',--排序字段及排序方向，如addDate desc,id desc（排序字段需通过selectOrderFldName指定，可为空）           -- 页码 
strWhere     varchar default  ''  -- 查询条件(注意: 不要加where) 
)
as
 sql1 varchar(6000);--sql=》sql1
 SelectWhere1 varchar(100);
 TimeList1 varchar(200);
 strWhere1 varchar(1000);
 orderFldDesc1  varchar(100);
begin

 sql1:='';
 SelectWhere1:=SelectWhere;
 TimeList1 :=TimeList;
 strWhere1 :=strWhere;
 orderFldDesc1:=orderFldDesc;
if not(SelectWhere1 is null or rtrim(SelectWhere1) = '' ) then
 SelectWhere1:=' where '||SelectWhere1;
end if;

if not(TimeList1 is null or rtrim(TimeList1) = '' ) then
 TimeList1:=' and '||TimeList1;
end if;

if not(orderFldDesc1 is null or rtrim(orderFldDesc1) = '' ) then
 orderFldDesc1:=' order by '||orderFldDesc1;
end if;

	if not(strWhere1 is null or rtrim(strWhere1) = '' ) then
	 strWhere1:=' where '||strWhere1;
        end if;
	
	if CryStyle='Month'--月报表
	then
	 sql1:='select '||strGetFields||' from (
	select Partment_Num,Partment_Name,AmMeterCount,ZGross,SelectTime from 
	(select count(AmMeter_ID)as AmMeterCount,sum(ZGross) as ZGross,Partment,SelectTime from (select a.AmMeter_ID,ZGross,Partment,SelectTime from T_MonthAm as a,AmMeter as b where a.AmMeter_ID=b.AmMeter_ID '||TimeList1||')T group by Partment,SelectTime)T1 
	left join (select DictionaryValue_ID as Partment,DictionaryValue_Num as Partment_Num,DictionaryValue_Value as Partment_Name from DictionaryValue where Dictionary_ID=1)T2
	on T1.Partment=T2.Partment)T '||strWhere1 ||orderFldDesc1;

	
	elsif CryStyle='Year'--年报表
	then
	
	 sql1:='select '||strGetFields||' from (
	select Partment_Num,Partment_Name,AmMeterCount,ZGross,SelectTime from 
	(select count(AmMeter_ID)as AmMeterCount,sum(ZGross) as ZGross,Partment,SelectTime from (select a.AmMeter_ID,ZGross,Partment ,substring(SelectTime,1,4) SelectTime from T_MonthAm as a,AmMeter as b where a.AmMeter_ID=b.AmMeter_ID '||TimeList1||')T group by Partment,SelectTime)T1 
	left join (select DictionaryValue_ID as Partment,DictionaryValue_Num as Partment_Num,DictionaryValue_Value as Partment_Name from DictionaryValue where Dictionary_ID=1)T2
	on T1.Partment=T2.Partment)T '||strWhere1 ||orderFldDesc1;
	end if;
	execute immediate sql1;
end;






CREATE or replace procedure D_PartmentStatisics
(
SelectWhere varchar default '',--搜索的条件
TimeList varchar default '',--搜索的条件
CryStyle varchar default 'Day',--报表统计类型
strGetFields varchar default '*', 
orderFldDesc  varchar default 'Partment_Num',--排序字段及排序方向，如addDate desc,id desc（排序字段需通过selectOrderFldName指定，可为空） 
strWhere     varchar default ''  -- 查询条件(注意: 不要加where) 
)
as
 sql1 varchar(6000);--sql=》sql1
 SelectWhere1 varchar(100);
 TimeList1 varchar(200);
 strWhere1 varchar(1000);
 orderFldDesc1  varchar(100);
begin

 sql1:='';
 SelectWhere1:=SelectWhere;
 TimeList1 :=TimeList;
 strWhere1 :=strWhere;
 orderFldDesc1:=orderFldDesc;
if not(SelectWhere1 is null or rtrim(SelectWhere1) = '' ) then
 SelectWhere1:=' where '||SelectWhere1;
end if;

if not(TimeList1 is null or rtrim(TimeList1) = '' ) then
 TimeList1:=' and '||TimeList1;
end if;

if not(orderFldDesc1 is null or rtrim(orderFldDesc1) = '' ) then
   orderFldDesc1:=' order by '||orderFldDesc1;
	if not(strWhere1 is null or rtrim(strWhere1) = '' ) then
	 strWhere1:=' where '||strWhere1;
         end if;

	if CryStyle='Day'--日报表
	then
	 sql1:='select '||strGetFields||' from (
	select Partment_Num,Partment_Name,AmMeterCount,ZGross from 
	(select count(AmMeter_ID)as AmMeterCount,sum(ZGross) as ZGross,Partment from (select a.AmMeter_ID,ZGross,Partment from T_DayAm as a,AmMeter as b where a.AmMeter_ID=b.AmMeter_ID '||TimeList1||')T group by Partment)T1 
	left join (select DictionaryValue_ID as Partment,DictionaryValue_Num as Partment_Num,DictionaryValue_Value as Partment_Name from DictionaryValue where Dictionary_ID=1)T2
	on T1.Partment=T2.Partment)T '||strWhere1||orderFldDesc1;
	
	elsif CryStyle='Month'--月报表
	then
	
	 sql1:='select '||strGetFields||' from (
	select Partment_Num,Partment_Name,AmMeterCount,ZGross from 
	(select count(AmMeter_ID)as AmMeterCount,sum(ZGross) as ZGross,Partment from (select a.AmMeter_ID,ZGross,Partment from T_MonthAm as a,AmMeter as b where a.AmMeter_ID=b.AmMeter_ID '||TimeList1||')T group by Partment)T1 
	left join (select DictionaryValue_ID as Partment,DictionaryValue_Num as Partment_Num,DictionaryValue_Value as Partment_Name from DictionaryValue where Dictionary_ID=1)T2
	on T1.Partment=T2.Partment)T '||strWhere1||orderFldDesc1;
	
	elsif CryStyle='Year'--年报表
	then
	 sql1:='select '||strGetFields||' from (
	select Partment_Num,Partment_Name,AmMeterCount,ZGross from 
	(select count(AmMeter_ID)as AmMeterCount,sum(ZGross) as ZGross,Partment from (select a.AmMeter_ID,ZGross,Partment from T_MonthAm as a,AmMeter as b where a.AmMeter_ID=b.AmMeter_ID '||TimeList1||')T group by Partment)T1 
	left join (select DictionaryValue_ID as Partment,DictionaryValue_Num as Partment_Num,DictionaryValue_Value as Partment_Name from DictionaryValue where Dictionary_ID=1)T2
	on T1.Partment=T2.Partment)T '||strWhere1||orderFldDesc1;
	
	elsif CryStyle='Hour'--小时报表
	then
		 sql1:='select '||strGetFields||' from (
	select Partment_Num,Partment_Name,AmMeterCount,ZGross from 
	(select count(AmMeter_ID)as AmMeterCount,sum(ZGross) as ZGross,Partment from (select a.AmMeter_ID,ZGross,Partment from T_HourAm as a,AmMeter as b where a.AmMeter_ID=b.AmMeter_ID '||TimeList1||')T group by Partment)T1 
	left join (select DictionaryValue_ID as Partment,DictionaryValue_Num as Partment_Num,DictionaryValue_Value as Partment_Name from DictionaryValue where Dictionary_ID=1)T2
	on T1.Partment=T2.Partment)T '||strWhere1||orderFldDesc1;
	end if;
end if;
	execute immediate sql1;

end;









CREATE or replace procedure D_PartmentWChainTime
(
SelectWhere varchar default '',--搜索的条件
TimeList varchar default '',--搜索的条件
TimeList2 varchar default '',--搜索的条件
CryStyle varchar default 'Month',--报表统计类型
strGetFields varchar default '*', 
orderFldDesc  varchar default '',--排序字段及排序方向，如addDate desc,id desc（排序字段需通过selectOrderFldName指定，可为空） 
strWhere     varchar default ''  -- 查询条件(注意: 不要加where) 
)
as
 sql1 varchar(6000);--sql=》sql1
 SelectWhere1 varchar(100);
 TimeListr varchar(200);
 TimeList2r varchar(200);
 strWhere1 varchar(1000);
begin

 sql1:='';
 SelectWhere1:=SelectWhere;
 TimeListr :=TimeList;
 strWhere1 :=strWhere;
 TimeList2r:=TimeList2;
if not(SelectWhere1 is null or rtrim(SelectWhere1) = '' ) then
 SelectWhere1:=' where '||SelectWhere1;
end if;

if not(TimeListr is null or rtrim(TimeListr) = '' ) then
 TimeListr:=' where '||TimeListr;
end if;

if not(TimeList2r is null or rtrim(TimeList2r) = '' ) then
 TimeList2r:=' where '||TimeList2r;

	if not(strWhere1 is null or rtrim(strWhere1) = '' ) then
	 strWhere1:=' where '||strWhere1;
        end if;
	
	if CryStyle='Month'--月报表
	then
	

	 sql1:='select '||strGetFields||' from (
select Partment_Num,Partment_Name,WaterMeterCount,BZGross,SZGross,(BZGross-SZGross)CGross,cast(cast((BZGross-SZGross)/SZGross*100 as numeric(12,3)) as varchar(20))||''%''  BFGross from 
(select Partment,sum(BZGross) BZGross,sum(SZGross) SZGross,Count(WaterMeter_ID)WaterMeterCount  from
(select T1.WaterMeter_ID,T1.ZGross BZGross,T2.ZGross SZGross,(select Partment from WaterMeter as a where a.WaterMeter_ID=T1.WaterMeter_ID)Partment from 
(select WaterMeter_ID,ZGross from T_MonthWater  '||TimeListr||')T1 inner join 
(select WaterMeter_ID,ZGross from T_MonthWater '||TimeList2r||')T2 on T1.WaterMeter_ID=T2.WaterMeter_ID)T3  group by Partment)T4
left join (select DictionaryValue_ID as Partment,DictionaryValue_Num as Partment_Num,DictionaryValue_Value as Partment_Name from DictionaryValue where Dictionary_ID=1)T5
on T4.Partment=T5.Partment
)T '||strWhere1;


	
	elsif CryStyle='Year'--年报表
	then

	
	 sql1:='select '||strGetFields||' from (
select Partment_Num,Partment_Name,WaterMeterCount,BZGross,SZGross,(BZGross-SZGross)CGross,cast(cast((BZGross-SZGross)/SZGross*100 as numeric(12,3)) as varchar(20))||''%''  BFGross from 
(select Partment,sum(BZGross) BZGross,sum(SZGross) SZGross,Count(WaterMeter_ID)WaterMeterCount  from
(select T1.WaterMeter_ID,T1.ZGross BZGross,T2.ZGross SZGross,(select Partment from WaterMeter as a where a.WaterMeter_ID=T1.WaterMeter_ID)Partment from 
(select WaterMeter_ID,ZGross from T_MonthWater  '||TimeListr||')T1 inner join 
(select WaterMeter_ID,ZGross from T_MonthWater '||TimeList2r||')T2 on T1.WaterMeter_ID=T2.WaterMeter_ID)T3  group by Partment)T4
left join (select DictionaryValue_ID as Partment,DictionaryValue_Num as Partment_Num,DictionaryValue_Value as Partment_Name from DictionaryValue where Dictionary_ID=1)T5
on T4.Partment=T5.Partment
)T '||strWhere1;


	end if;
    execute immediate sql1;
end if;
	
		
end;







CREATE or replace procedure D_PartmentWSameTime
(
SelectWhere varchar default '',--搜索的条件
TimeList varchar default '',--搜索的条件
CryStyle varchar default 'Month',--报表统计类型
strGetFields varchar default '*', 
orderFldDesc  varchar default 'Partment_Num',--排序字段及排序方向，如addDate desc,id desc（排序字段需通过selectOrderFldName指定，可为空）           -- 页码 
strWhere     varchar default ''  -- 查询条件(注意: 不要加where) 
)
as
 sql1 varchar(6000);--sql=》sql1
 SelectWhere1 varchar(100);
 TimeListr varchar(200);
 strWhere1 varchar(1000);
 orderFldDesc1  varchar(100);
begin

 sql1:='';
 SelectWhere1:=SelectWhere;
 TimeListr :=TimeList;
 strWhere1 :=strWhere;
 orderFldDesc1:= orderFldDesc;
if not(SelectWhere1 is null or rtrim(SelectWhere1) = '' ) then
 SelectWhere1:=' where '||SelectWhere1;
end if;

if not(TimeListr is null or rtrim(TimeListr) = '' ) then
 TimeListr:=' and '||TimeListr;
end if;

if not(orderFldDesc1 is null or rtrim(orderFldDesc1) = '' ) then
  orderFldDesc1:=' order by '||orderFldDesc1;

	if not(strWhere1 is null or rtrim(strWhere1) = '' ) then
	 strWhere1:=' where '||strWhere1;
        end if;
	
	if CryStyle='Month'--月报表
	then
	 sql1:='select '||strGetFields||' from (
	select Partment_Num,Partment_Name,WaterMeterCount,ZGross,SelectTime from 
	(select count(WaterMeter_ID)as WaterMeterCount,sum(ZGross) as ZGross,Partment,SelectTime from (select a.WaterMeter_ID,ZGross,Partment,SelectTime from T_MonthWater as a,WaterMeter as b where a.WaterMeter_ID=b.WaterMeter_ID '||TimeListr||')T group by Partment,SelectTime)T1 
	left join (select DictionaryValue_ID as Partment,DictionaryValue_Num as Partment_Num,DictionaryValue_Value as Partment_Name from DictionaryValue where Dictionary_ID=1)T2
	on T1.Partment=T2.Partment)T '||strWhere1 ||orderFldDesc1;

	
	elsif CryStyle='Year'--年报表
	then
	
	 sql1:='select '||strGetFields||' from (
	select Partment_Num,Partment_Name,WaterMeterCount,ZGross,SelectTime from 
	(select count(WaterMeter_ID)as WaterMeterCount,sum(ZGross) as ZGross,Partment,SelectTime from (select a.WaterMeter_ID,ZGross,Partment ,substring(SelectTime,1,4) SelectTime from T_MonthWater as a,WaterMeter as b where a.WaterMeter_ID=b.WaterMeter_ID '||TimeListr||')T group by Partment,SelectTime)T1 
	left join (select DictionaryValue_ID as Partment,DictionaryValue_Num as Partment_Num,DictionaryValue_Value as Partment_Name from DictionaryValue where Dictionary_ID=1)T2
	on T1.Partment=T2.Partment)T '||strWhere1 ||orderFldDesc1;
	end if;
	execute immediate sql1;
end if;
end;









CREATE or replace procedure D_PartmentWStatisics
(
SelectWhere varchar default '',--搜索的条件
TimeList varchar default '',--搜索的条件
CryStyle varchar default 'Day',--报表统计类型
strGetFields varchar default  '*', 
orderFldDesc  varchar default 'Partment_Num',--排序字段及排序方向，如addDate desc,id desc（排序字段需通过selectOrderFldName指定，可为空） 
strWhere     varchar default ''  -- 查询条件(注意: 不要加where) 
)
as
 sql1 varchar(6000);--sql=》sql1
 SelectWhere1 varchar(100);
 TimeListr varchar(200);
 strWhere1 varchar(1000);
 orderFldDesc1  varchar(100);
begin

 sql1:='';
 SelectWhere1:=SelectWhere;
 TimeListr :=TimeList;
 strWhere1 :=strWhere;
 orderFldDesc1:=orderFldDesc;
if not(SelectWhere1 is null or rtrim(SelectWhere1) = '' ) then
 SelectWhere1:=' where '||SelectWhere1;
end if;

if not(TimeListr is null or rtrim(TimeListr) = '' ) then
 TimeListr:=' and '||TimeListr;
end if;

if not(orderFldDesc1 is null or rtrim(orderFldDesc1) = '' ) then
 orderFldDesc1:=' order by '||orderFldDesc1;
	if not(strWhere1 is null or rtrim(strWhere1) = '' ) then
	 strWhere1:=' where '||strWhere1;
         end if;
	if CryStyle='Day'--日报表
	then
	 sql1:='select '||strGetFields||' from (
	select Partment_Num,Partment_Name,WaterMeterCount,ZGross from 
	(select count(WaterMeter_ID)as WaterMeterCount,sum(ZGross) as ZGross,Partment from (select a.WaterMeter_ID,ZGross,Partment from T_DayWater as a,WaterMeter as b where a.WaterMeter_ID=b.WaterMeter_ID '||TimeListr||')T group by Partment)T1 
	left join (select DictionaryValue_ID as Partment,DictionaryValue_Num as Partment_Num,DictionaryValue_Value as Partment_Name from DictionaryValue where Dictionary_ID=1)T2
	on T1.Partment=T2.Partment)T '||strWhere1||orderFldDesc1;
	
	elsif CryStyle='Month'--月报表
	then
	
	 sql1:='select '||strGetFields||' from (
	select Partment_Num,Partment_Name,WaterMeterCount,ZGross from 
	(select count(WaterMeter_ID)as WaterMeterCount,sum(ZGross) as ZGross,Partment from (select a.WaterMeter_ID,ZGross,Partment from T_MonthWater as a,WaterMeter as b where a.WaterMeter_ID=b.WaterMeter_ID '||TimeListr||')T group by Partment)T1 
	left join (select DictionaryValue_ID as Partment,DictionaryValue_Num as Partment_Num,DictionaryValue_Value as Partment_Name from DictionaryValue where Dictionary_ID=1)T2
	on T1.Partment=T2.Partment)T '||strWhere1||orderFldDesc1;
	
	elsif CryStyle='Year'--年报表
	then
	 sql1:='select '||strGetFields||' from (
	select Partment_Num,Partment_Name,WaterMeterCount,ZGross from 
	(select count(WaterMeter_ID)as WaterMeterCount,sum(ZGross) as ZGross,Partment from (select a.WaterMeter_ID,ZGross,Partment from T_MonthWater as a,WaterMeter as b where a.WaterMeter_ID=b.WaterMeter_ID '||TimeListr||')T group by Partment)T1 
	left join (select DictionaryValue_ID as Partment,DictionaryValue_Num as Partment_Num,DictionaryValue_Value as Partment_Name from DictionaryValue where Dictionary_ID=1)T2
	on T1.Partment=T2.Partment)T '||strWhere1||orderFldDesc1;
	
	elsif CryStyle='Hour'--小时报表
	then
		 sql1:='select '||strGetFields||' from (
	select Partment_Num,Partment_Name,WaterMeterCount,ZGross from 
	(select count(WaterMeter_ID)as WaterMeterCount,sum(ZGross) as ZGross,Partment from (select a.WaterMeter_ID,ZGross,Partment from T_HourWater as a,WaterMeter as b where a.WaterMeter_ID=b.WaterMeter_ID '||TimeListr||')T group by Partment)T1 
	left join (select DictionaryValue_ID as Partment,DictionaryValue_Num as Partment_Num,DictionaryValue_Value as Partment_Name from DictionaryValue where Dictionary_ID=1)T2
	on T1.Partment=T2.Partment)T '||strWhere1||orderFldDesc1;
	end if;

	execute immediate sql1;
end if;
end;









CREATE or replace procedure D_WaterAbnormalBoDong
(
SelectWhere varchar default '',--搜索的条件
TimeList varchar default '',--搜索的条件
strGetFields varchar default '*', 
doCount number default  0,--返回记录总数, 非 0 值则返回
orderFldDesc  varchar default '',--排序字段及排序方向，如addDate desc,id desc（排序字段需通过selectOrderFldName指定，可为空） 
PageSize     int default  10,           -- 页尺寸 ,如果为 0 不执行分页功能
PageIndex    int default  1,            -- 页码 
strWhere     varchar default  ''  -- 查询条件(注意: 不要加where) 
)
as
 sql1 varchar(6000);--sql=》sql1
 SelectWhere1 varchar(100);
 TimeListr varchar(200);
 strWhere1 varchar(1000);
 keyFldName      varchar(80);   -- 主键字段 
begin

 sql1:='';
 SelectWhere1:=SelectWhere;
 TimeListr :=TimeList;
 strWhere1 :=strWhere;
if not(SelectWhere1 is null or rtrim(SelectWhere1) = '' ) then
  SelectWhere1:=' where '||SelectWhere1;
end if;

if not(TimeListr is null or rtrim(TimeListr) = '' ) then
 TimeListr:=' where '||TimeListr;
end if;
if doCount=0 
then
	if not(strWhere1 is null or rtrim(strWhere1) = '' )  then
	 strWhere1:=' where '||strWhere1;

	 sql1:='select count(WaterMeter_ID) from (
		select A.WaterMeter_ID,convert(char(10),LastTime,120) as LastTime,convert(char(10),ThisTime,120) as ThisTime,LastGross,ThisGross,ABS(GrossCha)GrossCha ,cast((GrossCha/LastGross)*100 as numeric(8,2)) GrossPercent,InsertTime,(select Architecture_Name from Architecture as c where c.Architecture_ID=b.Architecture_ID)Architecture_Name,(select Gather_Name from Gather as c where c.Gather_ID=b.Gather_ID)Gather_Name,WaterMeter_Name,WaterMeter_485Address,ConsumerNum,ConsumerName from 
		(select WaterMeter_ID,LastTime,ThisTime,LastGross,ThisGross,(ThisGross-LastGross)GrossCha,InsertTime from WaterAbnormalBoDong '||TimeListr||')A,
		(select WaterMeter_ID,Architecture_ID,Gather_ID,WaterMeter_Name,WaterMeter_485Address,ConsumerNum,ConsumerName from WaterMeter '||SelectWhere1||')B 
		where A.WaterMeter_ID=B.WaterMeter_ID
		)T '||strWhere1;

	execute immediate sql1;
        end if;
else

	if not(strWhere1 is null or rtrim(strWhere1) = '' ) then
	 strWhere1:=' where '||strWhere1;
	
	 sql1:='select '||strGetFields||' from (
			select A.WaterMeter_ID,convert(char(10),LastTime,120) as LastTime,convert(char(10),ThisTime,120) as ThisTime,LastGross,ThisGross,ABS(GrossCha)GrossCha,cast((GrossCha/LastGross)*100 as numeric(8,2))GrossPercent,InsertTime,(select Architecture_Name from Architecture as c where c.Architecture_ID=b.Architecture_ID)Architecture_Name,(select Gather_Name from Gather as c where c.Gather_ID=b.Gather_ID)Gather_Name,WaterMeter_Name,WaterMeter_485Address,ConsumerNum,ConsumerName from 
		(select WaterMeter_ID,LastTime,ThisTime,LastGross,ThisGross,(ThisGross-LastGross)GrossCha,InsertTime from WaterAbnormalBoDong '||TimeListr||')A,
		(select WaterMeter_ID,Architecture_ID,Gather_ID,WaterMeter_Name,WaterMeter_485Address,ConsumerNum,ConsumerName from WaterMeter '||SelectWhere1||')B 
		where A.WaterMeter_ID=B.WaterMeter_ID
		)T '||strWhere1;
           end if;

	if PageSize<>0
	then
		
		 keyFldName:='WaterMeter_ID' ; 
	/*	exec SearchGetDataByPage sql1,orderFldDesc,keyFldName,PageSize,PageIndex  */
	
	else
	
		execute immediate sql1;
	end if;
end if;
end;









CREATE or replace procedure D_WaterAbnormalDaozuo
(
SelectWhere varchar default '',--搜索的条件
TimeList varchar default '',--搜索的条件
strGetFields varchar default  '*', 
doCount number default  0,--返回记录总数, 非 0 值则返回
orderFldDesc  varchar default '',--排序字段及排序方向，如addDate desc,id desc（排序字段需通过selectOrderFldName指定，可为空） 
PageSize     int default  10,           -- 页尺寸 ,如果为 0 不执行分页功能
PageIndex    int default  1,            -- 页码 
strWhere     varchar default ''  -- 查询条件(注意: 不要加where) 
)
as
 sql1 varchar(6000);--sql=》sql1
 SelectWhere1 varchar(100);
 TimeListr varchar(200);
 strWhere1 varchar(1000);
 keyFldName      varchar(80);   -- 主键字段 
begin

 sql1:='';
 SelectWhere1:=SelectWhere;
 TimeListr :=TimeList;
 strWhere1 :=strWhere;
if not(SelectWhere1 is null or rtrim(SelectWhere1) = '' ) then
 SelectWhere1:=' where '+SelectWhere1;
end if;

if not(TimeListr is null or rtrim(TimeListr) = '' ) then
 TimeListr:=' where '+TimeListr;
end if;

if doCount=0 
then
	if not(strWhere1 is null or rtrim(strWhere1) = '' ) then
	 strWhere1:=' where '+strWhere1;

	 sql1:='select count(WaterMeter_ID) from (
		select A.WaterMeter_ID,convert(char(10),LastTime,120) as LastTime,convert(char(10),ThisTime,120) as ThisTime,LastZValueZY,ThisZValueZY,InsertTime,(select Architecture_Name from Architecture as c where c.Architecture_ID=b.Architecture_ID)Architecture_Name,(select Gather_Name from Gather as c where c.Gather_ID=b.Gather_ID)Gather_Name,WaterMeter_Name,WaterMeter_485Address,ConsumerNum,ConsumerName from 
		(select WaterMeter_ID,LastTime,ThisTime,LastZValueZY,ThisZValueZY,InsertTime from WaterAbnormalDaozuo '+TimeListr+')A,
		(select WaterMeter_ID,Architecture_ID,Gather_ID,WaterMeter_Name,WaterMeter_485Address,ConsumerNum,ConsumerName from WaterMeter '+SelectWhere1+')B 
		where A.WaterMeter_ID=B.WaterMeter_ID
		)T '+strWhere1;

	execute immediate sql1;
           end if;
else

	if not(strWhere1 is null or rtrim(strWhere1) = '' )  then
	 strWhere1:=' where '+strWhere1;
	
	 sql1:='select '+strGetFields+' from (
			select A.WaterMeter_ID,convert(char(10),LastTime,120) as LastTime,convert(char(10),ThisTime,120) as ThisTime,LastZValueZY,ThisZValueZY,InsertTime,(select Architecture_Name from Architecture as c where c.Architecture_ID=b.Architecture_ID)Architecture_Name,(select Gather_Name from Gather as c where c.Gather_ID=b.Gather_ID)Gather_Name,WaterMeter_Name,WaterMeter_485Address,ConsumerNum,ConsumerName from 
		(select WaterMeter_ID,LastTime,ThisTime,LastZValueZY,ThisZValueZY,InsertTime from WaterAbnormalDaozuo '+TimeListr+')A,
		(select WaterMeter_ID,Architecture_ID,Gather_ID,WaterMeter_Name,WaterMeter_485Address,ConsumerNum,ConsumerName from WaterMeter '+SelectWhere1+')B 
		where A.WaterMeter_ID=B.WaterMeter_ID
		)T '+strWhere1;  
          end if;


	if PageSize<>0
	then
		 keyFldName:='WaterMeter_ID';  
	/*	exec SearchGetDataByPage sql1,orderFldDesc,keyFldName,PageSize,PageIndex */
	
	else
	
		execute immediate sql1;
	end if;
end if;
end;








CREATE or replace procedure D_WaterAbnormalMiss
(
SelectWhere varchar default '',--搜索的条件
TimeList varchar default '',--搜索的条件
strGetFields varchar default '*', 
doCount number default 0,--返回记录总数, 非 0 值则返回
orderFldDesc  varchar default '',--排序字段及排序方向，如addDate desc,id desc（排序字段需通过selectOrderFldName指定，可为空） 
PageSize     int default 10,           -- 页尺寸 ,如果为 0 不执行分页功能
PageIndex    int default 1,            -- 页码 
strWhere     varchar default ''  -- 查询条件(注意: 不要加where) 
)
as
 sql1 varchar(6000);--sql=》sql1
 SelectWhere1 varchar(100);
 TimeListr varchar(200);
 strWhere1 varchar(1000);
 keyFldName      varchar(80);   -- 主键字段 
begin

 sql1:='';
 SelectWhere1:=SelectWhere;
 TimeListr :=TimeList;
 strWhere1 :=strWhere;
if not(SelectWhere1 is null or rtrim(SelectWhere1) = '' ) then
 SelectWhere1:=' where '||SelectWhere1;
end if;

if not(TimeListr is null or rtrim(TimeListr) = '' )  then
 TimeListr:=' where '||TimeListr;
end if;

if doCount=0 
then
	if not(strWhere1 is null or rtrim(strWhere1) = '' ) then
	 strWhere1:=' where '||strWhere1;

	 sql1:='select count(WaterMeter_ID) from (
		select A.WaterMeter_ID,convert(char(10),ValueTime,120) as ValueTime,Again,InsertTime,(select Architecture_Name from Architecture as c where c.Architecture_ID=b.Architecture_ID)Architecture_Name,(select Gather_Name from Gather as c where c.Gather_ID=b.Gather_ID)Gather_Name,WaterMeter_Name,WaterMeter_485Address,ConsumerNum,ConsumerName from 
		(select WaterMeter_ID,ValueTime,Again,InsertTime from WaterAbnormalMiss '||TimeListr||')A,
		(select WaterMeter_ID,Architecture_ID,Gather_ID,WaterMeter_Name,WaterMeter_485Address,ConsumerNum,ConsumerName from WaterMeter '||SelectWhere1||')B 
		where A.WaterMeter_ID=B.WaterMeter_ID
		)T '||strWhere1;

	execute immediate sql1;
         end if;
else

	if not(strWhere1 is null or rtrim(strWhere1) = '' ) then
	 strWhere1:=' where '||strWhere1;
	
	 sql1:='select '||strGetFields||' from (
			select A.WaterMeter_ID,convert(char(10),ValueTime,120) as ValueTime,Again,InsertTime,(select Architecture_Name from Architecture as c where c.Architecture_ID=b.Architecture_ID)Architecture_Name,(select Gather_Name from Gather as c where c.Gather_ID=b.Gather_ID)Gather_Name,WaterMeter_Name,WaterMeter_485Address,ConsumerNum,ConsumerName from 
		(select WaterMeter_ID,ValueTime,Again,InsertTime from WaterAbnormalMiss '||TimeListr||')A,
		(select WaterMeter_ID,Architecture_ID,Gather_ID,WaterMeter_Name,WaterMeter_485Address,ConsumerNum,ConsumerName from WaterMeter '||SelectWhere1||')B 
		where A.WaterMeter_ID=B.WaterMeter_ID
		)T '||strWhere1;
         end if;

	if PageSize<>0
	then
		
	        keyFldName:='WaterMeter_ID';  
	/*	exec SearchGetDataByPage sql1,orderFldDesc,keyFldName,PageSize,PageIndex  */
	
	else
	
		execute immediate sql1;
	end if;
end if;
end;










CREATE or replace  procedure D_WaterAbnormalZroe
(
SelectWhere varchar default '',--搜索的条件
TimeList varchar default '',--搜索的条件
strGetFields varchar default '*', 
doCount number default 0,--返回记录总数, 非 0 值则返回
orderFldDesc  varchar default '',--排序字段及排序方向，如addDate desc,id desc（排序字段需通过selectOrderFldName指定，可为空） 
PageSize     int default  10,           -- 页尺寸 ,如果为 0 不执行分页功能
PageIndex    int default 1,            -- 页码 
strWhere     varchar default ''  -- 查询条件(注意: 不要加where) 
)
as
 sql1 varchar(6000);--sql=》sql1
 SelectWhere1 varchar(100);
 TimeListr varchar(200);
 strWhere1 varchar(1000);
 keyFldName      varchar(80);   -- 主键字段 
begin

 sql1:='';
 SelectWhere1:=SelectWhere;
 TimeListr :=TimeList;
 strWhere1 :=strWhere;
if not(SelectWhere1 is null or rtrim(SelectWhere1) = '' ) then
 SelectWhere1:=' where '||SelectWhere1;
end if;

if not(TimeListr is null or rtrim(TimeListr) = '' ) then
 TimeListr:=' where '||TimeListr;
end if;

if doCount=0 
then
	if not(strWhere1 is null or rtrim(strWhere1) = '' ) then
	 strWhere1:=' where '||strWhere1;

	 sql1:='select count(WaterMeter_ID) from (
		select A.WaterMeter_ID,convert(char(10),ValueTime,120) as ValueTime,Again,InsertTime,(select Architecture_Name from Architecture as c where c.Architecture_ID=b.Architecture_ID)Architecture_Name,(select Gather_Name from Gather as c where c.Gather_ID=b.Gather_ID)Gather_Name,WaterMeter_Name,WaterMeter_485Address,ConsumerNum,ConsumerName from 
		(select WaterMeter_ID,ValueTime,Again,InsertTime from WaterAbnormalZroe '||TimeListr||')A,
		(select WaterMeter_ID,Architecture_ID,Gather_ID,WaterMeter_Name,WaterMeter_485Address,ConsumerNum,ConsumerName from WaterMeter '||SelectWhere1||')B 
		where A.WaterMeter_ID=B.WaterMeter_ID
		)T '||strWhere1;

	execute immediate sql1;
          end if;
else

	if not(strWhere1 is null or rtrim(strWhere1) = '' ) then
	 strWhere1:=' where '||strWhere1;
	
	 sql1:='select '||strGetFields||' from (
			select A.WaterMeter_ID,convert(char(10),ValueTime,120) as ValueTime,Again,InsertTime,(select Architecture_Name from Architecture as c where c.Architecture_ID=b.Architecture_ID)Architecture_Name,(select Gather_Name from Gather as c where c.Gather_ID=b.Gather_ID)Gather_Name,WaterMeter_Name,WaterMeter_485Address,ConsumerNum,ConsumerName from 
		(select WaterMeter_ID,ValueTime,Again,InsertTime from WaterAbnormalZroe '||TimeListr||')A,
		(select WaterMeter_ID,Architecture_ID,Gather_ID,WaterMeter_Name,WaterMeter_485Address,ConsumerNum,ConsumerName from WaterMeter '||SelectWhere1||')B 
		where A.WaterMeter_ID=B.WaterMeter_ID
		)T '||strWhere1;
         end if;

	if PageSize<>0
	then
		
		 keyFldName:='WaterMeter_ID'; 
	/*	exec SearchGetDataByPage sql1,orderFldDesc,keyFldName,PageSize,PageIndex  */
	
	else
	
		execute immediate sql1;
	end if;
end if;
end;










CREATE or replace procedure DataSiteAdd
(
DataSite_Num varchar,
DataSite_Name varchar,
DataSite_IP varchar,
DataSite_Prot int,
DataSite_Heart int,
DataSite_Remark varchar,
Result out int  
)
as
begin
		insert into DataSite(DataSite_Num,DataSite_Name,DataSite_IP,DataSite_Prot,DataSite_Heart,DataSite_Remark)
		values(DataSite_Num,DataSite_Name,DataSite_IP,DataSite_Prot,DataSite_Heart,DataSite_Remark);
		select max(DataSite_ID) into Result from DataSite;
end;









CREATE or replace procedure DataSiteDel
(
DataSite_ID int
)
as
begin
delete from DataSite where DataSite_ID=DataSite_ID;
end;








CREATE or replace procedure DataSiteEdit
(
DataSite_ID int,
DataSite_Num varchar,
DataSite_Name varchar,
DataSite_IP varchar,
DataSite_Prot int,
DataSite_Heart int,
DataSite_Remark varchar
)
as
begin
update DataSite set
 DataSite_Num=DataSite_Num,
DataSite_Name=DataSite_Name,
DataSite_IP=DataSite_IP,
DataSite_Prot=DataSite_Prot,
DataSite_Heart=DataSite_Heart,
DataSite_Remark=DataSite_Remark
where 
DataSite_ID=DataSite_ID;
end;













CREATE or replace procedure DictionaryValueAdd
(
Dictionary_ID int,
DictionaryValue_Num varchar,
DictionaryValue_Value varchar,
Result out int  
)
as
 i int;
begin
	
	 i:=0;
	select count(DictionaryValue_ID) into i from DictionaryValue where Dictionary_ID=Dictionary_ID and DictionaryValue_Num=DictionaryValue_Num;
	if i>0
	then
		 Result:=0;
	
	else
	
		insert into DictionaryValue(Dictionary_ID,DictionaryValue_Num,DictionaryValue_Value) values(Dictionary_ID,DictionaryValue_Num,DictionaryValue_Value);
		 Result:=1;
	end if;
end;










CREATE or replace procedure DictionaryValueDel
(
DictionaryValue_ID int
)
as
begin

	delete from DictionaryValue where DictionaryValue_ID=DictionaryValue_ID;
end;









CREATE or replace procedure DictionaryValueEdit
(
DictionaryValue_ID int,
Dictionary_ID int,
DictionaryValue_Num varchar,
DictionaryValue_Value varchar,
Result out int  
)
as
 i int;
begin
	
	 i:=0;
	select count(DictionaryValue_ID) into i from DictionaryValue where Dictionary_ID=Dictionary_ID and DictionaryValue_Num=DictionaryValue_Num and DictionaryValue_ID<>DictionaryValue_ID;
	if i>0
	then
		 Result:=0;
	
	else
	
		update DictionaryValue set Dictionary_ID=Dictionary_ID,DictionaryValue_Num=DictionaryValue_Num,DictionaryValue_Value=DictionaryValue_Value where DictionaryValue_ID=DictionaryValue_ID;
		 Result:=1;
	end if;
end;



create or replace procedure EditMeterStyleTexingValue
(
MeteStyle_ID int ,
MeterTexing_ID int,
TexingValue varchar
)
as
 i int;
begin

 i:=0;
select count(MeteStyle_ID) into i from TexingValue where MeterTexing_ID=MeterTexing_ID and MeteStyle_ID=MeteStyle_ID;
if i=0
then
	insert into TexingValue(MeteStyle_ID,MeterTexing_ID,TexingValue) values(MeteStyle_ID,MeterTexing_ID,TexingValue);

else

	update TexingValue set TexingValue=TexingValue  where MeterTexing_ID=MeterTexing_ID and MeteStyle_ID=MeteStyle_ID;
end if;
end;






CREATE or replace  procedure FeilvDel
(
Feilv_ID int
)
as
begin

delete from Feilv where Feilv_ID=Feilv_ID;
delete from FeilvList where Feilv_ID=Feilv_ID;
end;







CREATE or replace procedure GatherAdd
(
Area_ID int,
DataSite_ID int,
Gather_State int,
Gather_Num varchar,
Gather_Name varchar,
Gather_Address varchar,
Gather_Password varchar,
Gather_AnzhuangAddress varchar,
Gather_Changshang varchar,
Gather_Banben varchar,
Gather_Size varchar,
Protocol varchar,
SendWay int,
Gather_Style int,
Result out int 
)
as
 c int;
begin
 Result:=0;

select count(Gather_ID) into c from Gather where Gather_Address=Gather_Address;
if c=0
then
insert into Gather(Area_ID,DataSite_ID,Gather_State,Gather_Num,Gather_Name,Gather_Address,Gather_Password,Gather_AnzhuangAddress,Gather_Changshang,Gather_Banben,Gather_Size,Protocol,SendWay,Gather_Style) 
values(Area_ID,DataSite_ID,Gather_State,Gather_Num,Gather_Name,Gather_Address,Gather_Password,Gather_AnzhuangAddress,Gather_Changshang,Gather_Banben,Gather_Size,Protocol,SendWay,Gather_Style);
select max(Gather_ID) into Result from Gather;
end if;
end;





CREATE or replace procedure GatherDel
(
Gather_ID int,
Result out int 
)
as
 cout int; --count=>cout
begin

 Result:=0;
 cout:=0;
select count(AmMeter_ID) into cout from AmMeter where Gather_ID=Gather_ID;
if cout=0
then
delete from  Gather where Gather_ID=Gather_ID ;
end if;
end;






CREATE or replace  procedure GatherEdit
(
Gather_ID int,
Area_ID int,
DataSite_ID int,
Gather_State int,
Gather_Num varchar,
Gather_Name varchar,
Gather_Address varchar,
Gather_Password varchar,
Gather_AnzhuangAddress varchar,
Gather_Changshang varchar,
Gather_Banben varchar,
Gather_Size varchar,
Protocol varchar,
SendWay int,
Gather_Style int,
Result out int 
)
as
 c int;
begin

 Result:=0;

select count(Gather_ID) into c from Gather where Gather_Address=Gather_Address and Gather_ID<>Gather_ID;
if c=0
then
	update Gather set Area_ID=Area_ID,
DataSite_ID=DataSite_ID,
Gather_State=Gather_State,
Gather_Num=Gather_Num,
Gather_Name=Gather_Name,
Gather_Address=Gather_Address,
Gather_Password=Gather_Password,
Gather_AnzhuangAddress=Gather_AnzhuangAddress,
Gather_Changshang=Gather_Changshang,
Gather_Banben=Gather_Banben,
Gather_Size=Gather_Size,
SendWay=SendWay,
Gather_Style=Gather_Style
	where Gather_ID=Gather_ID;
 Result:=Gather_ID;
end if;
end;






CREATE or replace Procedure GatherSelect
(
SelectWhere varchar default '',--默认第一层过滤
strGetFields varchar default '*', 
doCount number default 0,--返回记录总数, 非 0 值则返回
orderFldDesc  varchar default '',--排序字段及排序方向，如addDate desc,id desc（排序字段需通过selectOrderFldName指定，可为空） 
PageSize     int default  10,           -- 页尺寸 ,如果为 0 不执行分页功能
PageIndex    int default  1,            -- 页码 
strWhere     varchar default  ''  -- 查询条件(注意: 不要加where) 
)
as 
 sql1 varchar(6000);--sql=>sql1
 SelectWhere1 varchar(1500);
 strWhere1    varchar(1000);
 keyFldName      varchar(80);   -- 主键字段 
begin

/*Gather_ID,Area_ID,Architecture_ID,DataSite_ID,Gather_State,Gather_Num,Gather_Name,Gather_Address,Gather_Password,Gather_AnzhuangAddress,Gather_Changshang,Gather_Banben,Gather_Size,Protocol,Gather_MFrozen,Gather_DFrozen,Gather_Interval */

 sql1:='';
 SelectWhere1:=SelectWhere;
 strWhere1:=strWhere;
if not(SelectWhere1 is null or rtrim(SelectWhere1) = '' ) then
 SelectWhere1:=' where '||SelectWhere1;
end if;

if doCount=1 
then
	if not(strWhere1 is null or rtrim(strWhere1) = '') then
		 strWhere1:=' where '||strWhere1;  
          end if;
	 sql1:='select '||strGetFields||' from(select Gather_ID,(Select Area_Name from Area as a where T1.Area_ID=a.Area_ID) Area_Name, (Select DataSite_Name from DataSite as a where T1.DataSite_ID=a.DataSite_ID) DataSite_Name,Gather_State,Gather_Num,Gather_Name,Gather_Address,Gather_Password,Gather_AnzhuangAddress,Gather_Changshang,Gather_Banben,Gather_Size,Protocol,SendWay,case gather_style when 1 then ''主站轮抄'' else ''主动上报'' end as gather_style from (select * from Gather '||SelectWhere1||')T1 )T2 '||strWhere1;
	if PageSize<>0
	then
		
		 keyFldName:='Gather_ID' ; 
	/*	exec SearchGetDataByPage sql1,orderFldDesc,keyFldName,PageSize,PageIndex */
	
	else
	
		execute immediate sql1;
	end if;

else

	if strWhere1 is null or rtrim(strWhere1) = ''
	then
		 sql1:='select Count(Gather_ID) from Gather '||SelectWhere1;
	
	else
	
		 strWhere1:=' where '||strWhere1;
		 sql1:='select Count(Gather_ID) from(select Gather_ID,(Select Area_Name from Area as a where T1.Area_ID=a.Area_ID) Area_Name,(Select DataSite_Name from DataSite as a where T1.DataSite_ID=a.DataSite_ID) DataSite_Name,Gather_State,Gather_Num,Gather_Name,Gather_Address,Gather_Password,Gather_AnzhuangAddress,Gather_Changshang,Gather_Banben,Gather_Size,Protocol,SendWay,case gather_style when 1 then ''主站轮抄'' else ''主动上报'' end as gather_style from (select * from Gather '||SelectWhere1||')T1 )T2 '||strWhere1;
	end if;

	execute immediate sql1;
end if;
end;







CREATE  or replace  Procedure GetAmBaojing
(
SelectWhere varchar default '',--默认第一层过滤
strGetFields varchar default  '*', 
doCount number default 0,--返回记录总数, 非 0 值则返回
orderFldDesc  varchar default '',--排序字段及排序方向，如addDate desc,id desc（排序字段需通过selectOrderFldName指定，可为空） 
PageSize     int default  10,           -- 页尺寸 ,如果为 0 不执行分页功能
PageIndex    int default  1,            -- 页码 
strWhere     varchar default ''  -- 查询条件(注意: 不要加where) 
)
as 
 sql1 varchar(6000);--sql=>sql1
 SelectWhere1 varchar(1500);
 strWhere1    varchar(1000);
 keyFldName      varchar(80);   -- 主键字段 
begin
 sql1:='';
 SelectWhere1:=SelectWhere;
 strWhere1:=strWhere;
if not(SelectWhere1 is null or rtrim(SelectWhere1) = '' ) then
 SelectWhere1:=' where '+SelectWhere1;
end if;

if doCount=0 
then
	if strWhere1 is null or rtrim(strWhere1) = ''
	then
		 sql1:='select Count(AmBaojing_ID) from AmBaojing '+SelectWhere1;
	
	else
	
		 strWhere1:=' where '+strWhere1;
		 sql1:='select Count(AmBaojing_ID) from(select AmBaojing_ID,b.AmMeter_Name,(select Area_Name from Area as c where c.Area_ID=b.Area_ID)Area_Name,(select Architecture_Name from Architecture as c where c.Architecture_ID= b.Architecture_ID)Architecture_Name,ConsumerNum,ConsumerName,AmBaojing_Time,AmBaojing_Remark,AmBaojing_SendSMS,inserttime from 
(select AmBaojing_ID,AmMeter_ID,AmBaojing_Time,AmBaojing_Remark,AmBaojing_SendSMS,inserttime from AmBaojing '+SelectWhere1+') as a,AmMeter as b where a.AmMeter_ID=b.AmMeter_ID )T2 '+strWhere1;
	end if;

	execute immediate sql1;

else


	if not(strWhere1 is null or rtrim(strWhere1) = '') then
		strWhere1:=' where '+strWhere1;
        end if;
	 sql1:='select '+strGetFields+' from(select AmBaojing_ID,b.AmMeter_Name,(select Area_Name from Area as c where c.Area_ID=b.Area_ID)Area_Name,(select Architecture_Name from Architecture as c where c.Architecture_ID= b.Architecture_ID)Architecture_Name,ConsumerNum,ConsumerName,AmBaojing_Time,AmBaojing_Remark,AmBaojing_SendSMS,inserttime from 
(select AmBaojing_ID,AmMeter_ID,AmBaojing_Time,AmBaojing_Remark,AmBaojing_SendSMS,inserttime from AmBaojing '+SelectWhere1+') as a,AmMeter as b where a.AmMeter_ID=b.AmMeter_ID  )T2 '+strWhere1;
	if PageSize<>0
	then
		
		 keyFldName:='AmBaojing_ID';
		--print sql1
	/*	exec SearchGetDataByPage sql1,orderFldDesc,keyFldName,PageSize,PageIndex  */
	
	else
	

		execute immediate sql1;
	end if;

end if;
end;









CREATE or replace procedure GetChildMenu
(
Users_ID int ,
Parent_ID int
)
AS
begin
select distinct Module_ID,Module_Name,Module_Num,Module_Address,Module_Image from V_UsersToModule where Users_ID=Users_ID and Roles_Enable=1 and Module_Part1='是' and Module_Parent=Parent_ID order by Module_Num;
end;





CREATE or replace  procedure GetMeterDownInfo
(
Meter_ID int
)
as
 sql1 varchar(8000);
begin




 sql1:='select 
			AmMeter_Point as Meter_Point,
			MeteStyle_ID,
			(
				select 
					TexingValue as TongxunSulv 
				from 
					TexingValue as c 
				where 
					c.MeteStyle_ID=a.MeteStyle_ID and 
					c.MeterTexing_ID=5
				) as TongxunSulv,
			isnull(
				(
					select 
						TexingValue as Port 
					from 
						TexingValue as c 
					where  
						c.MeteStyle_ID=a.MeteStyle_ID and 
						c.MeterTexing_ID=19
				),5) as Meter_Port,
			(
				select 
					TexingValue as TongxunXieyi 
				from 
					TexingValue as c 
				where  
					c.MeteStyle_ID=a.MeteStyle_ID and 
					c.MeterTexing_ID=18
			) as TongxunXieyi,
			AmMeter_485Address as Meter_485Address,
			AmMeter_Password  as Meter_Password,
			(
				select 
					TexingValue as Feilv 
				from 
					TexingValue as c 
				where  
					c.MeteStyle_ID=a.MeteStyle_ID and 
					c.MeterTexing_ID=2
			) as Feilv,
                        (
				select 
					MeteStyle_Num
				from 
					MeteStyle as c 
				where  
					c.MeteStyle_ID=a.MeteStyle_ID
			) as MeteStyle_Num,
			Gather_ID,
			''000000000000'' as  Gather_TongxinAddress
		from 
			AmMeter a 
		where 
			AmMeter_ID='||to_char(Meter_ID);

--print sql1
execute immediate sql1;
end;







CREATE or replace procedure GetSLHistory
(
strGetFields varchar default '*', 
PageSize int default  10, 
PageIndex int default 1, 
doCount number default 0,
strWhere varchar default  ''
)
AS
 sql1 varchar(6000);
 strWhere1 varchar(1500);
begin
strWhere1:=strWhere;
sql1:='';
if not(strWhere1 is null or rtrim(strWhere1) = '' )  then
 strWhere1:=' where '||strWhere1;
end if;

if doCount=0 
then
 sql1:='select count(SLHistory_ID) from (select SLHistory_ID,SLHistory_Time,SLHistory_State,(select Users_Name from Users as b where a.Users_ID=b.Users_ID)User_Name,SLHistory_Style,SLHistory_Opr from SLHistory as a '||strWhere1||')T';
	execute immediate sql1;

else


 sql1:= 'select SLHistory_ID,SLHistory_Time,SLHistory_State,isnull((select Users_Name from Users as b where a.Users_ID=b.Users_ID),''系统'')Users_Name,SLHistory_Style,SLHistory_Opr from SLHistory as a '||strWhere1;
end if;

if PageSize<>0
then
/*	exec SearchGetDataByPage sql1,'','SLHistory_ID',PageSize,PageIndex  */
  null;
else

	execute immediate sql1;
end if;
end;








CREATE  or replace Procedure GetTargetAm
(
SelectWhere varchar default '',--默认第一层过滤
strGetFields varchar default '*', 
doCount number default 0,--返回记录总数, 非 0 值则返回
orderFldDesc  varchar default '',--排序字段及排序方向，如addDate desc,id desc（排序字段需通过selectOrderFldName指定，可为空） 
PageSize     int default  10,           -- 页尺寸 ,如果为 0 不执行分页功能
PageIndex    int default 1,            -- 页码 
strWhere     varchar default ''  -- 查询条件(注意: 不要加where) 
)
as 
 SelectWhere1 varchar(1500);
 sql1 varchar(6000);
 strWhere1 varchar(1500);
 keyFldName      varchar(80);   -- 主键字段 
begin
 SelectWhere1:=SelectWhere;
 strWhere1:=strWhere;
 sql1:='';
if not(SelectWhere1 is null or rtrim(SelectWhere1) = '' ) then
  SelectWhere1:=' where '||SelectWhere1;
end if;

if doCount=0 
then
	if strWhere1 is null or rtrim(strWhere1) = ''
	then
		 sql1:='select Count(TargetAmDate_ID) from TargetAmDate'||SelectWhere1;
	
	else
	
		 strWhere1:=' where '||strWhere1;
		 sql1:='select Count(TargetAmDate_ID) from(select TargetAmDate_ID,TargetAm_Index,TargetAm_Style,TargetAmDate_Time,TargetAmGross,TargetAmDate_ZGross,TargetAmDate_Cha,TargetAmDate_Per,TargetAmDate_Remark,inserttime from  TargetAmDate '||SelectWhere1||' )T2 '||strWhere1;
	end if;

	execute immediate sql1;

else


	if not(strWhere1 is null or rtrim(strWhere1) = '')  then
		 strWhere1:=' where '||strWhere1;
         end if;
	 sql1:='select '||strGetFields||' from(select TargetAmDate_ID,TargetAm_Index,TargetAm_Style,TargetAmDate_Time,TargetAmGross,TargetAmDate_ZGross,TargetAmDate_Cha,TargetAmDate_Per,TargetAmDate_Remark,inserttime from  TargetAmDate '||SelectWhere1||' )T2 '||strWhere1;
	if PageSize<>0
	then
		
		 keyFldName:='TargetAmDate_ID';  
		--print sql1
	/*	exec SearchGetDataByPage sql1,orderFldDesc,keyFldName,PageSize,PageIndex  */
	
	else
	 

		execute immediate sql1;
	end if;

end if;
end;














CREATE or replace procedure GetUsersInfo
(
strGetFields varchar default '*', 
PageSize int default 10, 
PageIndex int default 1, 
doCount number default 0,
strWhere varchar default  ''''
)
AS
begin
/* exec Pageing 'V_UsersInfo',strGetFields,'Users_ID',PageSize,PageIndex,doCount,1,strWhere */
null;
end;









CREATE or replace procedure GetWaMeterDownInfo
(
Meter_ID int
)
as
 sql1 varchar(8000);
begin




 sql1:='select 
			WaterMeter_Point as Meter_Point,
			MeteStyle_ID,
			(
				select 
					TexingValue as TongxunSulv 
				from 
					TexingValue as c 
				where 
					c.MeteStyle_ID=a.MeteStyle_ID and 
					c.MeterTexing_ID=23
				) as TongxunSulv,
			isnull(
				(
					select 
						TexingValue as Port 
					from 
						TexingValue as c 
					where  
						c.MeteStyle_ID=a.MeteStyle_ID and 
						c.MeterTexing_ID=28
				),5) as Meter_Port,
			(
				select 
					TexingValue as TongxunXieyi 
				from 
					TexingValue as c 
				where  
					c.MeteStyle_ID=a.MeteStyle_ID and 
					c.MeterTexing_ID=27
			) as TongxunXieyi,
			WaterMeter_485Address as Meter_485Address,
			WaterMeter_Password  as Meter_Password,
			(
				select 
					TexingValue as Feilv 
				from 
					TexingValue as c 
				where  
					c.MeteStyle_ID=a.MeteStyle_ID and 
					c.MeterTexing_ID=20
			) as Feilv,
			(
				select 
					d.DictionaryValue_Value as makecode 
				from 
					TexingValue as c,
					DictionaryValue as d
				where  
					c.MeteStyle_ID=a.MeteStyle_ID			and 
					c.MeterTexing_ID=29						and
					c.TexingValue = d.DictionaryValue_Num	and
					d.Dictionary_ID = 27
			) as makecode,
			(
				select 
					MeteStyle_Num
				from 
					MeteStyle as c 
				where  
					c.MeteStyle_ID=a.MeteStyle_ID
			) as MeteStyle_Num,
			Gather_ID,
			''000000000000'' as  Gather_TongxinAddress 
		from 
			WaterMeter a 
		where 
			WaterMeter_ID='+to_char(Meter_ID);

--print sql
execute immediate sql1;
end;





CREATE or replace  Procedure GetWaterBaojing
(
SelectWhere varchar default '',--默认第一层过滤
strGetFields varchar default  '*', 
doCount number default  0,--返回记录总数, 非 0 值则返回
orderFldDesc  varchar default '',--排序字段及排序方向，如addDate desc,id desc（排序字段需通过selectOrderFldName指定，可为空） 
PageSize     int default  10,           -- 页尺寸 ,如果为 0 不执行分页功能
PageIndex    int default  1,            -- 页码 
strWhere     varchar default  ''  -- 查询条件(注意: 不要加where) 
)
as 
 SelectWhere1 varchar(1500);
 sql1 varchar(6000);
 strWhere1 varchar(1500);
 keyFldName      varchar(80);   -- 主键字段 
begin
SelectWhere1:=SelectWhere;
 strWhere1:=strWhere;
 sql1:='';
if not(SelectWhere1 is null or rtrim(SelectWhere1) = '' ) then
 SelectWhere1:=' where '||SelectWhere1;
end if;

if doCount=0 
then
	if strWhere1 is null or rtrim(strWhere1) = ''
	then
		 sql1:='select Count(WaterBaojing_ID) from WaterBaojing '||SelectWhere1;
	
	else
	
		 strWhere1:=' where '||strWhere1;
		 sql1:='select Count(WaterBaojing_ID) from(select WaterBaojing_ID,b.WaterMeter_Name,(select Area_Name from Area as c where c.Area_ID=b.Area_ID)Area_Name,(select Architecture_Name from Architecture as c where c.Architecture_ID= b.Architecture_ID)Architecture_Name,ConsumerNum,ConsumerName,WaterBaojing_Time,WaterBaojing_Remark,WaterBaojing_SendSMS,inserttime from 
(select WaterBaojing_ID,WaterMeter_ID,WaterBaojing_Time,WaterBaojing_Remark,WaterBaojing_SendSMS,inserttime from WaterBaojing '||SelectWhere1||') as a,WaterMeter as b where a.WaterMeter_ID=b.WaterMeter_ID )T2 '||strWhere1;
	end if;

	execute immediate sql1;

else


	if not(strWhere1 is null or rtrim(strWhere1) = '') then
		 strWhere1:=' where '||strWhere1;
        end if;

	 sql1:='select '||strGetFields||' from(select WaterBaojing_ID,b.WaterMeter_Name,(select Area_Name from Area as c where c.Area_ID=b.Area_ID)Area_Name,(select Architecture_Name from Architecture as c where c.Architecture_ID= b.Architecture_ID)Architecture_Name,ConsumerNum,ConsumerName,WaterBaojing_Time,WaterBaojing_Remark,WaterBaojing_SendSMS,inserttime from 
(select WaterBaojing_ID,WaterMeter_ID,WaterBaojing_Time,WaterBaojing_Remark,WaterBaojing_SendSMS,inserttime from WaterBaojing '||SelectWhere1||') as a,WaterMeter as b where a.WaterMeter_ID=b.WaterMeter_ID  )T2 '||strWhere1;
	if PageSize<>0
	then
		
		 keyFldName:='WaterBaojing_ID' ; 
		--print sql1
	/*	exec SearchGetDataByPage sql1,orderFldDesc,keyFldName,PageSize,PageIndex  */
	
	else
	 

		execute immediate sql1;
	end if;

end if;
end;










CREATE or replace  Procedure GetWaterLeak
(
SelectWhere varchar default '',--默认第一层过滤
strGetFields varchar default  '*', 
doCount number default  0,--返回记录总数, 非 0 值则返回
orderFldDesc  varchar default '',--排序字段及排序方向，如addDate desc,id desc（排序字段需通过selectOrderFldName指定，可为空） 
PageSize     int default  10,           -- 页尺寸 ,如果为 0 不执行分页功能
PageIndex    int default  1,            -- 页码 
strWhere     varchar default  ''  -- 查询条件(注意: 不要加where) 
)
as 
 SelectWhere1 varchar(1500);
 sql1 varchar(6000);
 strWhere1 varchar(1500);
 keyFldName      varchar(80);   -- 主键字段 

begin
SelectWhere1:=SelectWhere;
 strWhere1:=strWhere;
 sql1:='';
if not(SelectWhere1 is null or rtrim(SelectWhere1) = '' ) then
 SelectWhere1:=' where '||SelectWhere1;
end if;

if doCount=0 
then
	if strWhere1 is null or rtrim(strWhere1) = ''
	then
		 sql1:='select Count(WaterLeak_ID) from WaterLeak '||SelectWhere1;
	
	else
	
		 strWhere1:=' where '||strWhere1;
		 sql1:='select Count(WaterLeak_ID) from(select WaterLeak_ID,b.WaterMeter_Name,(select Area_Name from Area as c where c.Area_ID=b.Area_ID)Area_Name,(select Architecture_Name from Architecture as c where c.Architecture_ID= b.Architecture_ID)Architecture_Name,WaterLeak_Time,WaterLeak_Style,a.WaterMeter_ID,WaterLeak_ZGross,WaterLeak_SumGross,WaterLeak_CGross,WaterLeak_Percent,insertTime from 
(select WaterLeak_ID,WaterLeak_Time,(select DictionaryValue_Value from DictionaryValue where Dictionary_ID=15 and DictionaryValue_Num=WaterLeak_Style) WaterLeak_Style,WaterMeter_ID,WaterLeak_ZGross,WaterLeak_SumGross,WaterLeak_CGross,WaterLeak_Percent,insertTime from WaterLeak '||SelectWhere1||') as a,WaterMeter as b where a.WaterMeter_ID=b.WaterMeter_ID )T2 '||strWhere1;
	end if;

	execute immediate sql1;

else


	if not(strWhere1 is null or rtrim(strWhere1) = '')  then
		 strWhere1:=' where '||strWhere1;
        end if;
	 sql1:='select '||strGetFields||' from(select WaterLeak_ID,b.WaterMeter_Name,(select Area_Name from Area as c where c.Area_ID=b.Area_ID)Area_Name,(select Architecture_Name from Architecture as c where c.Architecture_ID= b.Architecture_ID)Architecture_Name,WaterLeak_Time,WaterLeak_Style,a.WaterMeter_ID,WaterLeak_ZGross,WaterLeak_SumGross,WaterLeak_CGross,WaterLeak_Percent,insertTime from 
(select WaterLeak_ID,WaterLeak_Time,(select DictionaryValue_Value from DictionaryValue where Dictionary_ID=15 and DictionaryValue_Num=WaterLeak_Style) WaterLeak_Style,WaterMeter_ID,WaterLeak_ZGross,WaterLeak_SumGross,WaterLeak_CGross,WaterLeak_Percent,insertTime from WaterLeak '||SelectWhere1||') as a,WaterMeter as b where a.WaterMeter_ID=b.WaterMeter_ID )T2 '||strWhere1;
	if PageSize<>0
	then
		 keyFldName:='WaterLeak_ID' ; 
		--print sql1
	/*	exec SearchGetDataByPage sql1,orderFldDesc,keyFldName,PageSize,PageIndex  */
	
	else
	 

		execute immediate sql1;
	end if;

end if;
end;


CREATE or replace  procedure MeteStyleAdd
(
MeteStyle_Num varchar,
MeteStyle_Name varchar,
MeteStyle_Type varchar,
MeteStyle_Changjia varchar,
MeteStyle_time date,
MeteStyle_Remark varchar,
Result out int  
)
as

	begin
		insert into MeteStyle(MeteStyle_Num,MeteStyle_Name,MeteStyle_Type,MeteStyle_Changjia,MeteStyle_time,MeteStyle_Remark)
			values(MeteStyle_Num,MeteStyle_Name,MeteStyle_Type,MeteStyle_Changjia,MeteStyle_time,MeteStyle_Remark);
		select max(MeteStyle_ID) into Result from MeteStyle;

	end;





CREATE or replace procedure MeteStyleDel
(
MeteStyle_ID int
)
as
begin
	delete from  MeteStyle where MeteStyle_ID=MeteStyle_ID;
end;








CREATE or replace procedure MeteStyleEdit
(
MeteStyle_ID int,
MeteStyle_Num varchar,
MeteStyle_Name varchar,
MeteStyle_Type varchar,
MeteStyle_Changjia varchar,
MeteStyle_time date,
MeteStyle_Remark varchar
)
as
begin
delete from TexingValue where MeteStyle_ID=MeteStyle_ID;
		update MeteStyle set MeteStyle_Num=MeteStyle_Num,
				MeteStyle_Name=MeteStyle_Name,
				MeteStyle_Type=MeteStyle_Type,
				MeteStyle_Changjia=MeteStyle_Changjia,
				MeteStyle_time=MeteStyle_time,
				MeteStyle_Remark=MeteStyle_Remark
		where MeteStyle_ID=MeteStyle_ID;

end;







CREATE or replace  procedure ModuleAdd
(
Module_Name varchar,
Module_Num varchar,
Module_Address varchar,
Module_Biaozhi varchar,
Module_Parent int,
Module_Part1 varchar,
Module_Part2 varchar,
Module_Image varchar,
Module_Remark varchar,
Module_Order int default 0,
Result out int  
)
as
 i int;
begin
	
	 i:=0;
	select count(Module_ID) into i from Module where Module_Num=Module_Num;
	if i>0
	then
	      Result:=0;
	else
	
		insert into Module(Module_Name,Module_Num,Module_Address,Module_Biaozhi,Module_Parent,Module_Part1,Module_Part2,Module_Image,Module_Remark,Module_Order)
            			values(Module_Name,Module_Num,Module_Address,Module_Biaozhi,Module_Parent,Module_Part1,Module_Part2,Module_Image,Module_Remark,Module_Order);
		select Module_ID into Result from Module where Module_Num=Module_Num;
	

	end if;
end;





CREATE or replace procedure ModuleDel
(
Module_ID int
)
as
begin
	delete from Module where Module_ID=Module_ID;
	delete from Roles_Power where Power_ID in (select a.Power_ID from Power  a where a.Module_ID=Module_ID);
	delete from Power where Module_ID=Module_ID;

end;











CREATE or replace procedure ModuleEdit
(
Module_ID int,
Module_Name varchar,
Module_Num varchar,
Module_Address varchar,
Module_Biaozhi varchar,
Module_Parent int,
Module_Part1 varchar,
Module_Part2 varchar,
Module_Image varchar,
Module_Remark varchar,
Module_Order int default 0,
Result out int 
)
as
 i int;
 oldNum varchar(20);
begin
	
	 i:=0;
	select count(Module_ID) into i from Module where Module_Num=Module_Num and Module_ID<>Module_ID;
	if i>0
	then
		 Result:=0;
	
	else
	
		
		select Module_Num into oldNum from Module where Module_ID=Module_ID;
	      update Module set Module_Name=Module_Name,
				Module_Num=Module_Num,
				Module_Address=Module_Address,
				Module_Biaozhi=Module_Biaozhi,
				Module_Parent=Module_Parent,
				Module_Part1=Module_Part1,
				Module_Part2=Module_Part2,
				Module_Image=Module_Image,
				Module_Remark=Module_Remark,
				Module_Order=Module_Order
			where Module_ID=Module_ID;
		
		update Power set Power_Num=replace(Power_Num,oldNum,Module_Num) where Module_ID=Module_ID;
		update Power set Power_Name=Module_Name where Power_Num=Module_Num+'0';
		 Result:=1;
	end if;
end;





CREATE or replace  PROCEDURE Pageing
/*
***************************************************************
** 千万数量级分页存储过程 **
***************************************************************
参数说明:
1.tblName :表名称,视图
2.strGetFields :需要返回的列 
3.fldName :排序的字段名
4.PageSize :页尺寸
5.PageIndex :页码
6.doCount :返回记录总数, 非 0 值则返回
7.OrderType :设置排序类型, 非 0 值则降序
8.strWhere:查询条件 (注意: 不要加 where)

***************************************************************/
(
tblName varchar,	
strGetFields varchar default '*', 
fldName varchar default '''',
PageSize int default  10, 
PageIndex int default  1, 
doCount number default  0,
OrderType number default  0,
strWhere varchar default  ''''
)
AS
 strSQL varchar(5000); -- 主语句
 strTmp varchar(110) ;-- 临时变量
 strOrder varchar(400); -- 排序类型
begin

	if doCount <> 0
		then
			if strWhere <>''''   then
				 strSQL:= 'select count(*) Total from ' || tblName || ' where '||strWhere;
			else
				 strSQL:='select count(*) Total from ' || tblName || '';
		         end if;
	--以上代码的意思是如果doCount传递过来的不是0，就执行总数统计。以下的所有代码都是doCount为0的情况：
	else
		
			if OrderType <> 0
				then
					 strTmp := '<(select min';
					 strOrder := ' order by ' || fldName ||' desc';
				--如果OrderType不是0，就执行降序，这句很重要！
				
			else
				
					 strTmp := '>(select max';
					 strOrder := ' order by ' || fldName ||' asc';
			  end if;
			
                       if PageIndex = 1
				then
					if strWhere <> ''''   then
						 strSQL:= 'select top ' || PageSize ||' '||strGetFields||' '|| 'from ' || tblName || ' where ' || strWhere || ' ' || strOrder;
					else
						 strSQL:='select top '|| PageSize ||' '||strGetFields|| ' from '|| tblName || ' '|| strOrder;
				--如果是第一页就执行以上代码，这样会加快执行速度
				       end if;
			else
				
				--以下代码赋予了strSQL以真正执行的SQL代码
					 strSQL:= 'select top ' || PageSize ||' '||strGetFields|| ' from '|| tblName || ' where ' || fldName || '' || strTmp || '('|| fldName || ') from (select top '|| (PageIndex-1)*PageSize || ' '|| fldName || ' from ' || tblName || '' || strOrder || ') as tblTmp)'|| strOrder;
					if strWhere <> ''''  then
						 strSQL:= 'select top ' || PageSize ||' '||strGetFields|| 'from '
						|| tblName || ' where ' || fldName || '' || strTmp || '('
				
						|| fldName || ') from (select top ' || (PageIndex-1)*PageSize || ' '
				
						|| fldName || ' from ' || tblName || ' where ' || strWhere || ' '
				
						|| strOrder || ') as tblTmp) and ' || strWhere || ' ' || strOrder;
					
                                        end if;
	           
	             end if;


	 end if;
	execute immediate strSQL;
end;













CREATE or replace procedure PartmentAdd
(
Partment_Num varchar,
Partment_Name varchar,
Partment_Parent int,
Partment_Man varchar,
Partment_Phone varchar,
Partment_Remark varchar,
UserID int,
Result out int  
)
as
 NameText varchar(200);
 IDs varchar(200);
 error int; --error的全局变量
begin

		insert into Partment(Partment_Num,Partment_Name,Partment_Parent,Partment_Man,Partment_Phone,Partment_Remark) 
			values(Partment_Num,Partment_Name,Partment_Parent,Partment_Man,Partment_Phone,Partment_Remark);
		
		if error =0  
     then	
		select max(Partment_ID) into Result from Partment;
		
                      NameText:=FunPartmentNameText(Result);
                         
                         
                       IDs:=FunPartmentIDText(Result);   
		
                          
UPDATE 	Partment SET 	Partment_Text=NameText ,Partment_IDs=IDs  WHERE 	Partment_ID=Result;
if UserID<>1
then
		insert into OprPartment_List values(1,Result);	
end if;
		insert into OprPartment_List values(UserID,Result);
 else
     Result:=0; 
end if;           
end;









CREATE or replace procedure PartmentDel
(
Partment_ID int,
Result out int  
)
as
cout int;
begin
 select count(*) into cout from Partment where Partment_Parent=Partment_ID;
if cout<>0
then
 Result:=0;
else

delete from Partment  where Partment_ID=Partment_ID;
delete from OprPartment_List where Partment_ID=Partment_ID;
delete from T_PartmentDayAm where Partment_ID=Partment_ID;
delete from T_PartmentDayGas where Partment_ID=Partment_ID;
delete from T_PartmentDayWater where Partment_ID=Partment_ID;
delete from T_PartmentFenleiDayAm where Partment_ID=Partment_ID;
delete from T_PartmentFenleiDayGas where Partment_ID=Partment_ID;
delete from T_PartmentFenleiDayWater where Partment_ID=Partment_ID;
delete from T_PartmentStyleDayAm where Partment_ID=Partment_ID;
delete from T_PartmentStyleDayGas where Partment_ID=Partment_ID;
delete from T_PartmentStyleDayWater where Partment_ID=Partment_ID;
 Result:=Partment_ID;

end if;


end;












CREATE or replace  procedure PartmentEdit
(
Partment_ID int,
Partment_Num varchar,
Partment_Name varchar,
Partment_Parent int,
Partment_Man varchar,
Partment_Phone varchar,
Partment_Remark varchar
)
as
begin


		update Partment set Partment_Num=Partment_Num,
Partment_Name=Partment_Name,
Partment_Parent=Partment_Parent,
Partment_Man=Partment_Man,
Partment_Phone=Partment_Phone,
Partment_Remark=Partment_Remark where Partment_ID=Partment_ID;

--		declare NameText varchar(200)
--		set NameText=dbo.FunPartmentNameText(Partment_ID)
--declare IDs varchar(200)
--                          set IDs=dbo.FunPartmentIDText(Partment_ID)
--
--
--UPDATE 	Partment SET 	Partment_Text=NameText ,Partment_IDs=IDs WHERE 	Partment_ID=Partment_ID
--因为更新可能修改 父节点，所以只要更新就重建所有节点的 结构字段
UPDATE 	Partment SET 	Partment_Text=FunPartmentNameText(Partment_ID) ,Partment_IDs=FunPartmentIDText(Partment_ID);
end;









CREATE or replace  procedure PD_GetPartLastLineID
(
Part_ID int 
)
as
 PartStyle_ID varchar(20);
 Part_Parent int;
 Parent int;
 ID varchar(20);
 IDText varchar(200);
 YID int;
begin

 PartStyle_ID:='00';
 Part_Parent:=0;
select Part_Parent into Part_Parent from PD_Part where Part_ID=Part_ID;

while(PartStyle_ID<>'03') loop
	
	 Parent:=0;
	 PartStyle_ID:='00';
	select Part_Parent into Parent from PD_Part where Part_ID=Part_Parent;
        select PartStyle_ID into PartStyle_ID from PD_Part where Part_ID=Part_Parent;
--dbms_output.put_line PartStyle_ID;
--dbms_output.put_line print Parent;
	 if(PartStyle_ID='03')
		then
			update PD_Part set Part_ParentLine=Part_Parent where Part_ID=Part_ID;
			exit;
		
		else
		
			if(Parent>0)
			then
				 Part_Parent:=Parent;
			
			else
			
			update PD_Part set Part_ParentLine=0 where Part_ID=Part_ID;
			exit;
			end if;
		end if;

 

end loop;



 Part_Parent:=1;
 ID:='';
 IDText:='';

 YID:=Part_ID;
while(Part_Parent<>0)  loop

 Part_Parent:=0;
	select to_char(Part_ID) into ID from PD_Part where Part_ID=YID;
        select Part_Parent into Part_Parent from PD_Part where Part_ID=YID;
	if(IDText='')
	then
		 IDText:=ID;
	else
	
		 IDText:=ID||','||IDText;
	end if;
	 YID:=Part_Parent;
end loop;

if IDText='' then
 IDText:='0';
end if;

UPDATE 	PD_Part SET 	Part_IDs=IDText  WHERE 	Part_ID=Part_ID;

end;









create or replace  procedure PD_GetPartNameText
(
Part_ID int 
)
as
 Name varchar(200);
 NameText varchar(500);
 Part_Parent int;
 Part_ID1 int ;
begin

 Part_Parent:=1;
 Name:='';
 NameText:='';
 Part_ID1:=Part_ID;
while(Part_Parent<>0) loop

 Part_Parent:=0;
	select Part_Name into Name from PD_Part where Part_ID=Part_ID1;
        select Part_Parent into Part_Parent from PD_Part where Part_ID=Part_ID1;
	if(NameText='')
	then
		 NameText:=Name;
	
	else
	
		 NameText:=Name||'.'||NameText;
	end if;
	 Part_ID1:=Part_Parent;
end loop;


--select NameText as PartNameText;注释掉了，oracle中没有这种机制
end;









CREATE or replace  procedure PD_PartCopyData
(
Part_ID int ,
Result out int 
)
as
 PartStyle_ID varchar(10);
begin

 Result:=0;

select PartStyle_ID into PartStyle_ID from PD_Part where Part_ID=Part_ID;
insert into PD_Part(PartStyle_ID,Part_Parent,State,Part_Num,Part_Name,Part_SCCJ,Part_TYRQ,Part_Remark)
select PartStyle_ID,Part_Parent,State,Part_Num,Part_Name,Part_SCCJ,Part_TYRQ,Part_Remark from PD_Part where Part_ID=Part_ID;

select max(Part_ID) into Result from PD_Part;
if(PartStyle_ID='01')--配电房
then
insert into PD_PartInfo01(Part_ID,Address,DYLevel) select Result,Address,DYLevel from PD_PartInfo01 where Part_ID=Part_ID;

elsif(PartStyle_ID='02')--变压器
then
insert into PD_PartInfo02(Part_ID,Man,ManPhone,GZPL,EDGL,EDDY,DYB,KZDL,KZSH,XL,DYSX,DYXX,XEDL,XEWG,GLYS) select Result,Man,ManPhone,GZPL,EDGL,EDDY,DYB,KZDL,KZSH,XL,DYSX,DYXX,XEDL,XEWG,GLYS from PD_PartInfo02 where Part_ID=Part_ID;

elsif(PartStyle_ID='03')--回路
then
insert into PD_PartInfo03(Part_ID,JLLX,JLID,GZPL,ZEDGL,AEDGL,BEDGL,CEDGL,EDDY,DYB,KZDL,KZSH,XL,ADYSX,BDYSX,CDYSX,ADYXX,BDYXX,CDYXX,AXEDL,BXEDL,CXEDL,ZXEWG,AXEWG,BXEWG,CXEWG,GLYS,Jiemian,Length,StartAddress,EndAddress,AutomaticCut,DLBalance,DYBalance,YGBalance,WGBalance) 
select Result,JLLX,0,GZPL,ZEDGL,AEDGL,BEDGL,CEDGL,EDDY,DYB,KZDL,KZSH,XL,ADYSX,BDYSX,CDYSX,ADYXX,BDYXX,CDYXX,AXEDL,BXEDL,CXEDL,ZXEWG,AXEWG,BXEWG,CXEWG,GLYS,Jiemian,Length,StartAddress,EndAddress,AutomaticCut,DLBalance,DYBalance,YGBalance,WGBalance from PD_PartInfo03 where Part_ID=Part_ID;

elsif(PartStyle_ID='04')--开关
then
insert into PD_PartInfo04(Part_ID,EDDY,EDDL) select Result,EDDY,EDDL from PD_PartInfo04 where Part_ID=Part_ID;
end if;
end;










CREATE or replace procedure PD_PartDel
(
Part_ID int ,
Result out int 
)
as
 Cout int;--count=>Cout
 PartStyle_ID varchar(10);
begin

 Result:=0;

 Cout:=0;
select count(Part_ID) into Cout from PD_Part where Part_Parent=Part_ID;
if(Cout>0)
then
 Result:=0;

else 

select PartStyle_ID into PartStyle_ID from PD_Part where Part_ID=Part_ID;
delete from PD_Part where Part_ID=Part_ID;
if(PartStyle_ID='01')--配电房
then
delete from PD_PartInfo01 where Part_ID=Part_ID;

elsif(PartStyle_ID='02')--配电房
then
delete from PD_PartInfo02 where Part_ID=Part_ID;

elsif(PartStyle_ID='03')--配电房
then
delete from PD_PartInfo03 where Part_ID=Part_ID;

elsif(PartStyle_ID='04')--配电房
then
delete from PD_PartInfo04 where Part_ID=Part_ID;
end if;
Result:=1;
end if;

end;







CREATE or replace procedure PowerAdd
(
Power_Name varchar,
Power_Num varchar,
Module_ID int,
Power_Level int,
Power_Remark varchar,
Result out int  
)
as
 i int;
 max int;
begin
	
	 i:=0;
	select count(Power_ID) into i from Power where Power_Num=Power_Num;
	if i>0
	then
		 Result:=1;
	
	else
	
		insert into Power(Power_Name,Power_Num,Module_ID,Power_Level,Power_Remark)
				values(Power_Name,Power_Num,Module_ID,Power_Level,Power_Remark);
	
	select max(Power_ID) into max from Power;
	insert into Roles_Power values(1,max);
		 Result:=0;
	end if;
end;









CREATE or replace procedure PowerDel
(
Power_ID int
)
as
begin
	delete from Power where Power_ID=Power_ID;
	delete from Roles_Power where Power_ID=Power_ID;
end;













CREATE or replace procedure PowerEdit
(
Power_ID int,
Power_Name varchar,
Power_Num varchar,
Module_ID int,
Power_Level int,
Power_Remark varchar,
Result out int  
)
as
 i int;
begin
	
	 i:=0;
	select count(Power_ID) into i from Power where Power_Num=Power_Num and Power_ID<>Power_ID;
	if i>0
	then
		 Result:=1;
	
	else
	
		update Power set Power_Name=Power_Name,Power_Num=Power_Num,Module_ID=Module_ID,Power_Level=Power_Level,Power_Remark=Power_Remark
		where Power_ID=Power_ID;
		 Result:=0;
	end if;
end;



































































