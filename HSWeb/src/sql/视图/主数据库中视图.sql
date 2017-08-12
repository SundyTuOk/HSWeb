


CREATE or replace VIEW V_RolesToModule
AS
SELECT     a.Roles_ID, a.Roles_Enable, b.Power_ID, b.Module_ID, b.Module_Name, b.Module_Num, b.Module_Parent, b.Module_Address, b.Module_Part1, 
                      b.Module_Image,Module_Order
FROM         Roles  a INNER JOIN
                      Roles_Power  c ON a.Roles_ID = c.Roles_ID INNER JOIN
                      V_ModuleToPower  b ON c.Power_ID = b.Power_ID;

/*
EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPane1', @value=N'[0E232FF0-B466-11cf-A24F-00AA00A3EFFF, 1.00]
Begin DesignProperties = 
   Begin PaneConfigurations = 
      Begin PaneConfiguration = 0
         NumPanes = 4
         Configuration = "(H (1[50] 4[12] 2[21] 3) )"
      End
      Begin PaneConfiguration = 1
         NumPanes = 3
         Configuration = "(H (1 [50] 4 [25] 3))"
      End
      Begin PaneConfiguration = 2
         NumPanes = 3
         Configuration = "(H (1 [50] 2 [25] 3))"
      End
      Begin PaneConfiguration = 3
         NumPanes = 3
         Configuration = "(H (4 [30] 2 [40] 3))"
      End
      Begin PaneConfiguration = 4
         NumPanes = 2
         Configuration = "(H (1 [56] 3))"
      End
      Begin PaneConfiguration = 5
         NumPanes = 2
         Configuration = "(H (2 [66] 3))"
      End
      Begin PaneConfiguration = 6
         NumPanes = 2
         Configuration = "(H (4 [50] 3))"
      End
      Begin PaneConfiguration = 7
         NumPanes = 1
         Configuration = "(V (3))"
      End
      Begin PaneConfiguration = 8
         NumPanes = 3
         Configuration = "(H (1[56] 4[18] 2) )"
      End
      Begin PaneConfiguration = 9
         NumPanes = 2
         Configuration = "(H (1 [75] 4))"
      End
      Begin PaneConfiguration = 10
         NumPanes = 2
         Configuration = "(H (1[66] 2) )"
      End
      Begin PaneConfiguration = 11
         NumPanes = 2
         Configuration = "(H (4 [60] 2))"
      End
      Begin PaneConfiguration = 12
         NumPanes = 1
         Configuration = "(H (1) )"
      End
      Begin PaneConfiguration = 13
         NumPanes = 1
         Configuration = "(V (4))"
      End
      Begin PaneConfiguration = 14
         NumPanes = 1
         Configuration = "(V (2))"
      End
      ActivePaneConfig = 0
   End
   Begin DiagramPane = 
      Begin Origin = 
         Top = 0
         Left = 0
      End
      Begin Tables = 
         Begin Table = "a"
            Begin Extent = 
               Top = 6
               Left = 38
               Bottom = 125
               Right = 195
            End
            DisplayFlags = 280
            TopColumn = 0
         End
         Begin Table = "c"
            Begin Extent = 
               Top = 6
               Left = 233
               Bottom = 95
               Right = 375
            End
            DisplayFlags = 280
            TopColumn = 0
         End
         Begin Table = "b"
            Begin Extent = 
               Top = 3
               Left = 476
               Bottom = 122
               Right = 644
            End
            DisplayFlags = 280
            TopColumn = 0
         End
      End
   End
   Begin SQLPane = 
   End
   Begin DataPane = 
      Begin ParameterDefaults = ""
      End
   End
   Begin CriteriaPane = 
      Begin ColumnWidths = 11
         Column = 1440
         Alias = 900
         Table = 1170
         Output = 720
         Append = 1400
         NewValue = 1170
         SortType = 1350
         SortOrder = 1410
         GroupBy = 1350
         Filter = 1350
         Or = 1350
         Or = 1350
         Or = 1350
      End
   End
End
' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'VIEW',@level1name=N'V_RolesToModule'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPaneCount', @value=1 , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'VIEW',@level1name=N'V_RolesToModule'

*/











CREATE or replace VIEW V_RolesToModule
AS
SELECT     a.Roles_ID, a.Roles_Enable, b.Power_ID, b.Module_ID, b.Module_Name, b.Module_Num, b.Module_Parent, b.Module_Address, b.Module_Part1, 
                      b.Module_Image,Module_Order
FROM         Roles  a INNER JOIN
                      Roles_Power  c ON a.Roles_ID = c.Roles_ID INNER JOIN
                      V_ModuleToPower  b ON c.Power_ID = b.Power_ID;
/*

EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPane1', @value=N'[0E232FF0-B466-11cf-A24F-00AA00A3EFFF, 1.00]
Begin DesignProperties = 
   Begin PaneConfigurations = 
      Begin PaneConfiguration = 0
         NumPanes = 4
         Configuration = "(H (1[50] 4[12] 2[21] 3) )"
      End
      Begin PaneConfiguration = 1
         NumPanes = 3
         Configuration = "(H (1 [50] 4 [25] 3))"
      End
      Begin PaneConfiguration = 2
         NumPanes = 3
         Configuration = "(H (1 [50] 2 [25] 3))"
      End
      Begin PaneConfiguration = 3
         NumPanes = 3
         Configuration = "(H (4 [30] 2 [40] 3))"
      End
      Begin PaneConfiguration = 4
         NumPanes = 2
         Configuration = "(H (1 [56] 3))"
      End
      Begin PaneConfiguration = 5
         NumPanes = 2
         Configuration = "(H (2 [66] 3))"
      End
      Begin PaneConfiguration = 6
         NumPanes = 2
         Configuration = "(H (4 [50] 3))"
      End
      Begin PaneConfiguration = 7
         NumPanes = 1
         Configuration = "(V (3))"
      End
      Begin PaneConfiguration = 8
         NumPanes = 3
         Configuration = "(H (1[56] 4[18] 2) )"
      End
      Begin PaneConfiguration = 9
         NumPanes = 2
         Configuration = "(H (1 [75] 4))"
      End
      Begin PaneConfiguration = 10
         NumPanes = 2
         Configuration = "(H (1[66] 2) )"
      End
      Begin PaneConfiguration = 11
         NumPanes = 2
         Configuration = "(H (4 [60] 2))"
      End
      Begin PaneConfiguration = 12
         NumPanes = 1
         Configuration = "(H (1) )"
      End
      Begin PaneConfiguration = 13
         NumPanes = 1
         Configuration = "(V (4))"
      End
      Begin PaneConfiguration = 14
         NumPanes = 1
         Configuration = "(V (2))"
      End
      ActivePaneConfig = 0
   End
   Begin DiagramPane = 
      Begin Origin = 
         Top = 0
         Left = 0
      End
      Begin Tables = 
         Begin Table = "a"
            Begin Extent = 
               Top = 6
               Left = 38
               Bottom = 125
               Right = 195
            End
            DisplayFlags = 280
            TopColumn = 0
         End
         Begin Table = "c"
            Begin Extent = 
               Top = 6
               Left = 233
               Bottom = 95
               Right = 375
            End
            DisplayFlags = 280
            TopColumn = 0
         End
         Begin Table = "b"
            Begin Extent = 
               Top = 3
               Left = 476
               Bottom = 122
               Right = 644
            End
            DisplayFlags = 280
            TopColumn = 0
         End
      End
   End
   Begin SQLPane = 
   End
   Begin DataPane = 
      Begin ParameterDefaults = ""
      End
   End
   Begin CriteriaPane = 
      Begin ColumnWidths = 11
         Column = 1440
         Alias = 900
         Table = 1170
         Output = 720
         Append = 1400
         NewValue = 1170
         SortType = 1350
         SortOrder = 1410
         GroupBy = 1350
         Filter = 1350
         Or = 1350
         Or = 1350
         Or = 1350
      End
   End
End
' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'VIEW',@level1name=N'V_RolesToModule'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPaneCount', @value=1 , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'VIEW',@level1name=N'V_RolesToModule'



*/





CREATE or replace VIEW V_RolesYoPower
as
select a.Roles_ID,a.Roles_Enable,b.Power_ID,b.Module_ID,b.Power_Num,b.Power_Name,b.Power_Level from Roles a,Power  b, Roles_Power c where a.Roles_ID=c.Roles_ID and b.Power_ID=c.Power_ID;




CREATE or replace View V_SystemLog
as
select a.SystemLog_ID,a.Users_ID,(select c.Users_Name from Users  c where  c.Users_ID=a.Users_ID)Users_Name,a.Module_Num,b.Module_ID,b.Module_Name,(b.Module_Name||'（'||b.Module_Num||'）' ) Module_Show ,a.SystemLog_Action,a.SystemLog_Result,a.SystemLog_Time,a.SystemLog_Message from SystemLog  a left join Module  b on a.Module_Num=b.Module_Num;





CREATE or replace view V_UsersInfo
as  
select c.Users_ID,c.Area_ID,c.Users_Num,c.Users_Name,c.Users_LoginName,c.Users_Gender,c.Users_Birth,c.Users_Photo,c.Users_Department,c.Users_Phone,c.Users_Phone1,c.Users_Phone2,c.Users_HomeAddress,c.Users_LastTime,c.Users_RegTime,d.DictionaryValue_Value  UserState,c.Users_Remark,c.Users_DepartmentID,c.UserState_ID from (
		select Users_ID,Area_ID,Users_Num,Users_Name,Users_LoginName,Users_Gender,Users_Birth,Users_Photo,b.Partment_Text  Users_Department,Users_Phone,Users_Phone1,Users_Phone2,Users_HomeAddress,Users_LastTime,Users_RegTime,UserState_ID,Users_Remark,a.Users_Department Users_DepartmentID from Users a left join Partment b on b.Partment_ID=a.Users_Department) c 
left join DictionaryValue d on c.UserState_ID=d.DictionaryValue_ID;




CREATE or replace View V_UsersToModule
as
select a.Users_ID,Users_Num,Users_LoginName,b.Roles_ID,b.Roles_Enable,b.Power_ID,b.Module_ID,b.Module_Name,b.Module_Num,b.Module_Parent,b.Module_Address,b.Module_Part1,b.Module_Image,Module_Order from Users  a ,V_RolesToModule  b,Users_Roles  c where a.Users_ID=c.Users_ID and b.Roles_ID=c.Roles_ID;



CREATE or replace view V_UserToPower
as
select distinct a.Users_ID,b.Power_ID,b.Power_Num,b.Power_Name,b.Power_Level from Users  a, V_RolesYoPower  b, Users_Roles  c where a.Users_ID=c.Users_ID and c.Roles_ID=b.Roles_ID and b.Roles_Enable=1;


CREATE or replace VIEW V_WaMeterToGather
AS
SELECT Gather.Gather_ID, Gather.Gather_Address, 
      WaterMeter.WaterMeter_ID, WaterMeter.WaterMeter_485Address
FROM Gather INNER JOIN
      WaterMeter ON Gather.Gather_ID = WaterMeter.Gather_ID;








CREATE or replace  VIEW V_WaterBaojing
AS
SELECT     b.WaterBaojing_ID, b.WaterBaojing_Style, b.WaterBaojing_Time, b.WaterMeter_ID, b.WaterBaojing_Remark, b.WaterBaojing_SendSMS, b.inserttime, r.Area_Name || '.' || a.Architecture_Name  || '.' || to_char(m.Floor) || 'F.' || m.WaterMeter_Name  WaterMeter_Name,
                       m.Area_ID, m.Architecture_ID, 
                      m.Floor
FROM         WaterBaojing b LEFT OUTER JOIN
                      WaterMeter  m ON m.WaterMeter_ID = b.WaterMeter_ID LEFT OUTER JOIN
                      Architecture  a ON a.Architecture_ID = m.Architecture_ID LEFT OUTER JOIN
                      Area  r ON r.Area_ID = m.Area_ID;
/*

EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPane1', @value=N'[0E232FF0-B466-11cf-A24F-00AA00A3EFFF, 1.00]
Begin DesignProperties = 
   Begin PaneConfigurations = 
      Begin PaneConfiguration = 0
         NumPanes = 4
         Configuration = "(H (1[40] 4[20] 2[20] 3) )"
      End
      Begin PaneConfiguration = 1
         NumPanes = 3
         Configuration = "(H (1 [50] 4 [25] 3))"
      End
      Begin PaneConfiguration = 2
         NumPanes = 3
         Configuration = "(H (1 [50] 2 [25] 3))"
      End
      Begin PaneConfiguration = 3
         NumPanes = 3
         Configuration = "(H (4 [30] 2 [40] 3))"
      End
      Begin PaneConfiguration = 4
         NumPanes = 2
         Configuration = "(H (1 [56] 3))"
      End
      Begin PaneConfiguration = 5
         NumPanes = 2
         Configuration = "(H (2 [66] 3))"
      End
      Begin PaneConfiguration = 6
         NumPanes = 2
         Configuration = "(H (4 [50] 3))"
      End
      Begin PaneConfiguration = 7
         NumPanes = 1
         Configuration = "(V (3))"
      End
      Begin PaneConfiguration = 8
         NumPanes = 3
         Configuration = "(H (1[56] 4[18] 2) )"
      End
      Begin PaneConfiguration = 9
         NumPanes = 2
         Configuration = "(H (1 [75] 4))"
      End
      Begin PaneConfiguration = 10
         NumPanes = 2
         Configuration = "(H (1[66] 2) )"
      End
      Begin PaneConfiguration = 11
         NumPanes = 2
         Configuration = "(H (4 [60] 2))"
      End
      Begin PaneConfiguration = 12
         NumPanes = 1
         Configuration = "(H (1) )"
      End
      Begin PaneConfiguration = 13
         NumPanes = 1
         Configuration = "(V (4))"
      End
      Begin PaneConfiguration = 14
         NumPanes = 1
         Configuration = "(V (2))"
      End
      ActivePaneConfig = 0
   End
   Begin DiagramPane = 
      Begin Origin = 
         Top = 0
         Left = 0
      End
      Begin Tables = 
         Begin Table = "b"
            Begin Extent = 
               Top = 6
               Left = 38
               Bottom = 125
               Right = 242
            End
            DisplayFlags = 280
            TopColumn = 0
         End
         Begin Table = "m"
            Begin Extent = 
               Top = 6
               Left = 280
               Bottom = 125
               Right = 490
            End
            DisplayFlags = 280
            TopColumn = 0
         End
         Begin Table = "a"
            Begin Extent = 
               Top = 6
               Left = 528
               Bottom = 125
               Right = 766
            End
            DisplayFlags = 280
            TopColumn = 0
         End
         Begin Table = "r"
            Begin Extent = 
               Top = 6
               Left = 804
               Bottom = 125
               Right = 961
            End
            DisplayFlags = 280
            TopColumn = 0
         End
      End
   End
   Begin SQLPane = 
   End
   Begin DataPane = 
      Begin ParameterDefaults = ""
      End
      Begin ColumnWidths = 9
         Width = 284
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
      End
   End
   Begin CriteriaPane = 
      Begin ColumnWidths = 11
         Column = 1440
         Alias = 900
         Table = 1170
         Output = 720
         Append = 1400
         NewValue = 1170
         SortType = 1350
         SortOrder = 1410
         GroupBy = 1350
         Filter = 1350
         Or = 1350
         Or = 1350
         Or = 1350
      End
   End
End
' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'VIEW',@level1name=N'V_WaterBaojing'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPaneCount', @value=1 , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'VIEW',@level1name=N'V_WaterBaojing'



*/


/*
SelectTime有问题
原来sqlserver就有问题
*/
/*
create or replace view V_WaterGross
as
select sum(ZGross) ZGross,SelectTime from (select ZGross,substr(SelectTime,6,2) SelectTime from T_MonthWater where substr(SelectTime,1,4)='2010') T group by SelectTime;


*/


CREATE or replace VIEW V_WaterMaintenance
AS
SELECT     b.Maint_ID, b.WaterMeter_ID, b.MaintTime, b.MaintRemark, b.MaintMan, r.Area_Name || '.' || a.Architecture_Name || '.' || to_char(m.Floor) 
                      || 'F.' || m.WaterMeter_Name  WaterMeter_Name, m.Area_ID, m.Architecture_ID, m.Floor
FROM         WaterMaintenance  b LEFT OUTER JOIN
                      WaterMeter  m ON m.WaterMeter_ID = b.WaterMeter_ID LEFT OUTER JOIN
                      Architecture  a ON a.Architecture_ID = m.Architecture_ID LEFT OUTER JOIN
                      Area  r ON r.Area_ID = m.Area_ID;
/*
EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPane1', @value=N'[0E232FF0-B466-11cf-A24F-00AA00A3EFFF, 1.00]
Begin DesignProperties = 
   Begin PaneConfigurations = 
      Begin PaneConfiguration = 0
         NumPanes = 4
         Configuration = "(H (1[40] 4[20] 2[20] 3) )"
      End
      Begin PaneConfiguration = 1
         NumPanes = 3
         Configuration = "(H (1 [50] 4 [25] 3))"
      End
      Begin PaneConfiguration = 2
         NumPanes = 3
         Configuration = "(H (1 [50] 2 [25] 3))"
      End
      Begin PaneConfiguration = 3
         NumPanes = 3
         Configuration = "(H (4 [30] 2 [40] 3))"
      End
      Begin PaneConfiguration = 4
         NumPanes = 2
         Configuration = "(H (1 [56] 3))"
      End
      Begin PaneConfiguration = 5
         NumPanes = 2
         Configuration = "(H (2 [66] 3))"
      End
      Begin PaneConfiguration = 6
         NumPanes = 2
         Configuration = "(H (4 [50] 3))"
      End
      Begin PaneConfiguration = 7
         NumPanes = 1
         Configuration = "(V (3))"
      End
      Begin PaneConfiguration = 8
         NumPanes = 3
         Configuration = "(H (1[56] 4[18] 2) )"
      End
      Begin PaneConfiguration = 9
         NumPanes = 2
         Configuration = "(H (1 [75] 4))"
      End
      Begin PaneConfiguration = 10
         NumPanes = 2
         Configuration = "(H (1[66] 2) )"
      End
      Begin PaneConfiguration = 11
         NumPanes = 2
         Configuration = "(H (4 [60] 2))"
      End
      Begin PaneConfiguration = 12
         NumPanes = 1
         Configuration = "(H (1) )"
      End
      Begin PaneConfiguration = 13
         NumPanes = 1
         Configuration = "(V (4))"
      End
      Begin PaneConfiguration = 14
         NumPanes = 1
         Configuration = "(V (2))"
      End
      ActivePaneConfig = 0
   End
   Begin DiagramPane = 
      Begin Origin = 
         Top = 0
         Left = 0
      End
      Begin Tables = 
         Begin Table = "b"
            Begin Extent = 
               Top = 6
               Left = 38
               Bottom = 125
               Right = 202
            End
            DisplayFlags = 280
            TopColumn = 0
         End
         Begin Table = "m"
            Begin Extent = 
               Top = 6
               Left = 240
               Bottom = 125
               Right = 450
            End
            DisplayFlags = 280
            TopColumn = 0
         End
         Begin Table = "a"
            Begin Extent = 
               Top = 6
               Left = 488
               Bottom = 125
               Right = 726
            End
            DisplayFlags = 280
            TopColumn = 0
         End
         Begin Table = "r"
            Begin Extent = 
               Top = 6
               Left = 764
               Bottom = 125
               Right = 921
            End
            DisplayFlags = 280
            TopColumn = 0
         End
      End
   End
   Begin SQLPane = 
   End
   Begin DataPane = 
      Begin ParameterDefaults = ""
      End
      Begin ColumnWidths = 9
         Width = 284
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
      End
   End
   Begin CriteriaPane = 
      Begin ColumnWidths = 11
         Column = 1440
         Alias = 900
         Table = 1170
         Output = 720
         Append = 1400
         NewValue = 1170
         SortType = 1350
         SortOrder = 1410
         GroupBy = 1350
         Filter = 1350
         Or = 1350
         Or = 1350
         Or = 1350
      End
   End
End
' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'VIEW',@level1name=N'V_WaterMaintenance'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPaneCount', @value=1 , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'VIEW',@level1name=N'V_WaterMaintenance'
GO
*/



CREATE or replace VIEW V_WaterMeter
AS
SELECT a.WaterMeter_ID, a.WaterMeter_Point, a.Area_ID, a.Architecture_ID, a.Gather_ID, 
      a.WaterMeter_Num, a.WaterMeter_Name, a.WaterMeter_Password, 
      a.WaterMeter_485Address, a.Maker, a.MakerCode, a.AssetNo, a.IsSupply, a.ZValue, 
      a.UseAmStyle, a.ConsumerNum, a.ConsumerName, a.ConsumerPhone, 
      a.ConsumerAddress, a.IsImportantUser, a.IsCountMeter, a.isComputation, a.Partment, 
      a.Floor, a.MeteStyle_ID, a.Price_ID, a.WaterMeter_Stat, a.IsOffAlarm, a.CostCheck, 
      a.StandByCheck, a.Xiuzheng, a.LastTime, p.Partment_Name  PartmentName, 
      p.Partment_Phone  PartmentPhone, b.Architecture_Phone  ArchitecturePhone, 
      q.Area_Phone  AreaPhone, r.Price_Value  Price, 
      e.DictionaryValue_Value  UseAmFX, x.DictionaryValue_Value  UseAmXZ, 
       Beilv, 
      q.Area_Name || '.' || b.Architecture_Name || '.F' || to_char(a.Floor) 
      || '.' || a.WaterMeter_Name  Meter_Name, p.Partment_Text  FullPartment
FROM WaterMeter a LEFT OUTER JOIN
      Partment p ON p.Partment_ID = a.Partment LEFT OUTER JOIN
      Price r ON r.Price_ID = a.Price_ID LEFT OUTER JOIN
          (SELECT DictionaryValue_Num, DictionaryValue_Value
         FROM DictionaryValue
         WHERE (Dictionary_ID = 25)) e ON 
      e.DictionaryValue_Num = SUBSTR(a.WaterMeter_Num, 14, 1) LEFT OUTER JOIN
          (SELECT DictionaryValue_Num, DictionaryValue_Value
         FROM DictionaryValue  DictionaryValue_1
         WHERE (Dictionary_ID = 26)) x ON 
      x.DictionaryValue_Num = a.UseAmStyle LEFT OUTER JOIN
          (SELECT MeteStyle_ID, to_number(TexingValue)  bl
         FROM TexingValue
         WHERE (MeterTexing_ID = 13)) t ON 
      t.MeteStyle_ID = a.MeteStyle_ID LEFT OUTER JOIN
      Architecture b ON b.Architecture_ID = a.Architecture_ID LEFT OUTER JOIN
      Area q ON q.Area_ID = a.Area_ID;
/*
EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPane1', @value=N'[0E232FF0-B466-11cf-A24F-00AA00A3EFFF, 1.00]
Begin DesignProperties = 
   Begin PaneConfigurations = 
      Begin PaneConfiguration = 0
         NumPanes = 4
         Configuration = "(H (1[41] 4[21] 2[12] 3) )"
      End
      Begin PaneConfiguration = 1
         NumPanes = 3
         Configuration = "(H (1 [50] 4 [25] 3))"
      End
      Begin PaneConfiguration = 2
         NumPanes = 3
         Configuration = "(H (1 [50] 2 [25] 3))"
      End
      Begin PaneConfiguration = 3
         NumPanes = 3
         Configuration = "(H (4 [30] 2 [40] 3))"
      End
      Begin PaneConfiguration = 4
         NumPanes = 2
         Configuration = "(H (1 [56] 3))"
      End
      Begin PaneConfiguration = 5
         NumPanes = 2
         Configuration = "(H (2 [66] 3))"
      End
      Begin PaneConfiguration = 6
         NumPanes = 2
         Configuration = "(H (4 [50] 3))"
      End
      Begin PaneConfiguration = 7
         NumPanes = 1
         Configuration = "(V (3))"
      End
      Begin PaneConfiguration = 8
         NumPanes = 3
         Configuration = "(H (1[56] 4[18] 2) )"
      End
      Begin PaneConfiguration = 9
         NumPanes = 2
         Configuration = "(H (1 [75] 4))"
      End
      Begin PaneConfiguration = 10
         NumPanes = 2
         Configuration = "(H (1[66] 2) )"
      End
      Begin PaneConfiguration = 11
         NumPanes = 2
         Configuration = "(H (4 [60] 2))"
      End
      Begin PaneConfiguration = 12
         NumPanes = 1
         Configuration = "(H (1) )"
      End
      Begin PaneConfiguration = 13
         NumPanes = 1
         Configuration = "(V (4))"
      End
      Begin PaneConfiguration = 14
         NumPanes = 1
         Configuration = "(V (2))"
      End
      ActivePaneConfig = 0
   End
   Begin DiagramPane = 
      Begin Origin = 
         Top = 0
         Left = 0
      End
      Begin Tables = 
         Begin Table = "a"
            Begin Extent = 
               Top = 6
               Left = 38
               Bottom = 125
               Right = 248
            End
            DisplayFlags = 280
            TopColumn = 0
         End
         Begin Table = "p"
            Begin Extent = 
               Top = 6
               Left = 286
               Bottom = 125
               Right = 461
            End
            DisplayFlags = 280
            TopColumn = 0
         End
         Begin Table = "r"
            Begin Extent = 
               Top = 6
               Left = 499
               Bottom = 125
               Right = 644
            End
            DisplayFlags = 280
            TopColumn = 0
         End
         Begin Table = "e"
            Begin Extent = 
               Top = 6
               Left = 682
               Bottom = 95
               Right = 877
            End
            DisplayFlags = 280
            TopColumn = 0
         End
         Begin Table = "x"
            Begin Extent = 
               Top = 96
               Left = 682
               Bottom = 185
               Right = 877
            End
            DisplayFlags = 280
            TopColumn = 0
         End
         Begin Table = "t"
            Begin Extent = 
               Top = 126
               Left = 38
               Bottom = 215
               Right = 192
            End
            DisplayFlags = 280
            TopColumn = 0
         End
         Begin Table = "b"
            Begin Extent = 
               Top = 126
               Left = 230
               Bottom = 245
               Right = 468
            End
            DisplayFlags = 280
            TopColumn = 0
         End
     ' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'VIEW',@level1name=N'V_WaterMeter'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPane2', @value=N'    Begin Table = "q"
            Begin Extent = 
               Top = 126
               Left = 506
               Bottom = 245
               Right = 663
            End
            DisplayFlags = 280
            TopColumn = 0
         End
      End
   End
   Begin SQLPane = 
   End
   Begin DataPane = 
      Begin ParameterDefaults = ""
      End
      Begin ColumnWidths = 9
         Width = 284
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
      End
   End
   Begin CriteriaPane = 
      Begin ColumnWidths = 11
         Column = 1440
         Alias = 900
         Table = 1170
         Output = 720
         Append = 1400
         NewValue = 1170
         SortType = 1350
         SortOrder = 1410
         GroupBy = 1350
         Filter = 1350
         Or = 1350
         Or = 1350
         Or = 1350
      End
   End
End
' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'VIEW',@level1name=N'V_WaterMeter'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPaneCount', @value=2 , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'VIEW',@level1name=N'V_WaterMeter'
*/







CREATE or replace view VES_AmMeter
as
select AmMeter_ID,Area_ID,Architecture_ID,Partment,AmMeter_Num,(select Gather_Address from Gather  b where a.Gather_ID=b.Gather_ID)Gather_Address,AmMeter_Point,AmMeter_485Address,AmMeter_Password,beilv,substr(AmMeter_Num,14,1)  Fenlei,substr(AmMeter_Num,15,1)  Fenlei01,substr(AmMeter_Num,16,1)  Fenlei02,UseAmStyle,IsCountMeter,isComputation,Xiuzheng,Price_ID from AmMeter  a;




CREATE or replace view VES_WaterMeter
as
select WaterMeter_ID,Area_ID,Architecture_ID,Partment,WaterMeter_Num,(select Gather_Address from Gather  b where a.Gather_ID=b.Gather_ID)Gather_Address,WaterMeter_Point,WaterMeter_485Address,WaterMeter_Password,beilv,substr(WaterMeter_Num,14,1)  Fenlei,substr(WaterMeter_Num,15,1)  Fenlei01,substr(WaterMeter_Num,16,1)  Fenlei02,UseAmStyle,IsCountMeter,isComputation,Xiuzheng,Price_ID from WaterMeter  a;




CREATE or replace VIEW V_tmp
AS
SELECT Architecture.Architecture_ID, Architecture.Area_ID, 
      Architecture.Architecture_Name,    
      T_ArcDayWater.SelectYear  WaterYear, 
      T_ArcDayWater.SelectMonth  WaterMonth, 
      T_ArcDayWater.SelectDay   WaterDay, 
      T_ArcDayWater.ZGross  WaterZGross, 
      T_ArcDayWater.ZMoney  WaterZMoney, 
      T_ArcDayGas.SelectYear  GasYear, 
      T_ArcDayGas.SelectMonth  GasMonth, 
      T_ArcDayGas.SelectDay  GasDay, 
      T_ArcDayGas.ZGross  GasZGross, 
      T_ArcDayGas.ZMoney  GasZmoney,    
      T_ArcDayAm.SelectYear  AmYear, 
      T_ArcDayAm.SelectMonth  AmMonth, 
      T_ArcDayAm.SelectDay  AmDay, 
      T_ArcDayAm.ZGross  AmZGross, 
      T_ArcDayAm.ZMoney  AmZmoney, 
      ManualDay.En07, --由于没有ManualDay.Manual_Time, 故删除了
      ManualDay.En10, 
      ManualDay.En11, 
      ManualDay.En12
FROM Architecture left JOIN
      ManualDay ON 
      Architecture.Architecture_ID = ManualDay.Architecture_ID 
    left JOIN
      T_ArcDayAm ON 
      Architecture.Architecture_ID = T_ArcDayAm.Architecture_ID 
   left JOIN
      T_ArcDayGas ON 
      T_ArcDayAm.Architecture_ID = T_ArcDayGas.Architecture_ID 
      and  T_ArcDayGas.SelectYear = T_ArcDayAm.SelectYear
    and  T_ArcDayGas.SelectMonth = T_ArcDayAm.SelectMonth
    and  T_ArcDayGas.SelectDay = T_ArcDayAm.SelectDay
left JOIN
      T_ArcDayWater ON 
      T_ArcDayGas.Architecture_ID = T_ArcDayWater.Architecture_ID
      and  T_ArcDayWater.SelectYear = T_ArcDayGas.SelectYear
    and  T_ArcDayWater.SelectMonth = T_ArcDayGas.SelectMonth
    and  T_ArcDayWater.SelectDay = T_ArcDayGas.SelectDay;

/*
/****** Object:  View SelectTheT_HourWater    Script Date: 02/20/2014 10:51:52 ******/





CREATE or replace View SelectTheT_HourWater
 as 
select * from T_HourWater201206  

/****** Object:  View SelectTheT_HourAm    Script Date: 02/20/2014 10:51:52 ******/



CREATE or replace View SelectTheT_HourAm
 as 
select * from T_HourAm201206 
*/

/****** Object:  View [dbo].[V_AmBaojing]    Script Date: 02/21/2014 15:57:00 ******/





CREATE or replace VIEW V_AmBaojing
AS
SELECT b.*, r.Area_Name || '.' || a.Architecture_Name ||'.' || TO_CHAR( m.Floor) 
      || 'F.' || m.AmMeter_Name  AmMeter_Name, m.Area_ID, m.Architecture_ID, 
      m.Floor
FROM AmBaojing b LEFT OUTER JOIN
      AmMeter m ON m.AmMeter_ID = b.AmMeter_ID LEFT OUTER JOIN
      Architecture a ON a.Architecture_ID = m.Architecture_ID LEFT OUTER JOIN
      Area r ON r.Area_ID = m.Area_ID;



/****** EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPane1', @value=N'[0E232FF0-B466-11cf-A24F-00AA00A3EFFF, 1.00]
Begin DesignProperties = 
   Begin PaneConfigurations = 
      Begin PaneConfiguration = 0
         NumPanes = 4
         Configuration = "(H (1[40] 4[20] 2[20] 3) )"
      End
      Begin PaneConfiguration = 1
         NumPanes = 3
         Configuration = "(H (1 [50] 4 [25] 3))"
      End
      Begin PaneConfiguration = 2
         NumPanes = 3
         Configuration = "(H (1[50] 2[25] 3) )"
      End
      Begin PaneConfiguration = 3
         NumPanes = 3
         Configuration = "(H (4 [30] 2 [40] 3))"
      End
      Begin PaneConfiguration = 4
         NumPanes = 2
         Configuration = "(H (1 [56] 3))"
      End
      Begin PaneConfiguration = 5
         NumPanes = 2
         Configuration = "(H (2 [66] 3))"
      End
      Begin PaneConfiguration = 6
         NumPanes = 2
         Configuration = "(H (4 [50] 3))"
      End
      Begin PaneConfiguration = 7
         NumPanes = 1
         Configuration = "(V (3))"
      End
      Begin PaneConfiguration = 8
         NumPanes = 3
         Configuration = "(H (1 [56] 4 [18] 2))"
      End
      Begin PaneConfiguration = 9
         NumPanes = 2
         Configuration = "(H (1 [75] 4))"
      End
      Begin PaneConfiguration = 10
         NumPanes = 2
         Configuration = "(H (1[66] 2) )"
      End
      Begin PaneConfiguration = 11
         NumPanes = 2
         Configuration = "(H (4 [60] 2))"
      End
      Begin PaneConfiguration = 12
         NumPanes = 1
         Configuration = "(H (1) )"
      End
      Begin PaneConfiguration = 13
         NumPanes = 1
         Configuration = "(V (4))"
      End
      Begin PaneConfiguration = 14
         NumPanes = 1
         Configuration = "(V (2))"
      End
      ActivePaneConfig = 0
   End
   Begin DiagramPane = 
      Begin Origin = 
         Top = 0
         Left = 0
      End
      Begin Tables = 
         Begin Table = "b"
            Begin Extent = 
               Top = 6
               Left = 38
               Bottom = 109
               Right = 220
            End
            DisplayFlags = 280
            TopColumn = 0
         End
         Begin Table = "m"
            Begin Extent = 
               Top = 6
               Left = 258
               Bottom = 109
               Right = 446
            End
            DisplayFlags = 280
            TopColumn = 0
         End
         Begin Table = "a"
            Begin Extent = 
               Top = 6
               Left = 484
               Bottom = 109
               Right = 726
            End
            DisplayFlags = 280
            TopColumn = 0
         End
         Begin Table = "r"
            Begin Extent = 
               Top = 6
               Left = 764
               Bottom = 109
               Right = 916
            End
            DisplayFlags = 280
            TopColumn = 0
         End
      End
   End
   Begin SQLPane = 
   End
   Begin DataPane = 
      Begin ParameterDefaults = ""
      End
      RowHeights = 240
      Begin ColumnWidths = 12
         Width = 284
         Width = 1440
         Width = 1440
         Width = 1440
         Width = 1440
         Width = 1440
         Width = 1440
         Width = 1440
         Width = 1440
         Width = 1440
         Width = 1440
         Width = 1440
      End
   End
   Begin CriteriaPane = 
      Begin ColumnWidths = 11
         Column = 1440
         Alias = 900
         Table = 1170
         Output = 720
         Append = 1400
         NewValue = 1170
         SortType = 1350
         SortOrder = 1410
         GroupBy = 1350
         Filter = 1350
      ' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'VIEW',@level1name=N'V_AmBaojing'


EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPane2', @value=N'   Or = 1350
         Or = 1350
         Or = 1350
      End
   End
End
' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'VIEW',@level1name=N'V_AmBaojing'


EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPaneCount', @value=2 , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'VIEW',@level1name=N'V_AmBaojing' ******/


/****** Object:  View [dbo].[V_AmGross]    Script Date: 02/21/2014 16:16:46 ******/
 


 


/* 
CREATE or replace   view V_AmGross
as
select sum(ZGross) ZGross,SelectTime from (select ZGross,substr(SelectTime,6,2) SelectTime from T_MonthAm where substr(SelectTime,1,4)='2010')T group by SelectTime
*/

 


/****** Object:  View  V_AmMaintenance     Script Date: 02/21/2014 16:27:05 ******/
 

 


CREATE or replace View  V_AmMaintenance
AS
SELECT b.*, r.Area_Name || '.' || a.Architecture_Name || '.' || TO_CHAR( m.Floor) 
      || 'F.' || m.AmMeter_Name   AmMeter_Name, m.Area_ID, m.Architecture_ID, 
      m.Floor
FROM AmMaintenance b LEFT OUTER JOIN
      AmMeter m ON m.AmMeter_ID = b.AmMeter_ID LEFT OUTER JOIN
      Architecture a ON a.Architecture_ID = m.Architecture_ID LEFT OUTER JOIN
      Area r ON r.Area_ID = m.Area_ID;



/****** EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPane1', @value=N'[0E232FF0-B466-11cf-A24F-00AA00A3EFFF, 1.00]
Begin DesignProperties = 
   Begin PaneConfigurations = 
      Begin PaneConfiguration = 0
         NumPanes = 4
         Configuration = "(H (1[40] 4[20] 2[20] 3) )"
      End
      Begin PaneConfiguration = 1
         NumPanes = 3
         Configuration = "(H (1 [50] 4 [25] 3))"
      End
      Begin PaneConfiguration = 2
         NumPanes = 3
         Configuration = "(H (1[50] 2[25] 3) )"
      End
      Begin PaneConfiguration = 3
         NumPanes = 3
         Configuration = "(H (4 [30] 2 [40] 3))"
      End
      Begin PaneConfiguration = 4
         NumPanes = 2
         Configuration = "(H (1 [56] 3))"
      End
      Begin PaneConfiguration = 5
         NumPanes = 2
         Configuration = "(H (2 [66] 3))"
      End
      Begin PaneConfiguration = 6
         NumPanes = 2
         Configuration = "(H (4 [50] 3))"
      End
      Begin PaneConfiguration = 7
         NumPanes = 1
         Configuration = "(V (3))"
      End
      Begin PaneConfiguration = 8
         NumPanes = 3
         Configuration = "(H (1 [56] 4 [18] 2))"
      End
      Begin PaneConfiguration = 9
         NumPanes = 2
         Configuration = "(H (1 [75] 4))"
      End
      Begin PaneConfiguration = 10
         NumPanes = 2
         Configuration = "(H (1[66] 2) )"
      End
      Begin PaneConfiguration = 11
         NumPanes = 2
         Configuration = "(H (4 [60] 2))"
      End
      Begin PaneConfiguration = 12
         NumPanes = 1
         Configuration = "(H (1) )"
      End
      Begin PaneConfiguration = 13
         NumPanes = 1
         Configuration = "(V (4))"
      End
      Begin PaneConfiguration = 14
         NumPanes = 1
         Configuration = "(V (2))"
      End
      ActivePaneConfig = 0
   End
   Begin DiagramPane = 
      Begin Origin = 
         Top = 0
         Left = 0
      End
      Begin Tables = 
         Begin Table = "b"
            Begin Extent = 
               Top = 6
               Left = 38
               Bottom = 109
               Right = 184
            End
            DisplayFlags = 280
            TopColumn = 0
         End
         Begin Table = "m"
            Begin Extent = 
               Top = 6
               Left = 222
               Bottom = 109
               Right = 410
            End
            DisplayFlags = 280
            TopColumn = 0
         End
         Begin Table = "a"
            Begin Extent = 
               Top = 6
               Left = 448
               Bottom = 109
               Right = 690
            End
            DisplayFlags = 280
            TopColumn = 0
         End
         Begin Table = "r"
            Begin Extent = 
               Top = 6
               Left = 728
               Bottom = 109
               Right = 880
            End
            DisplayFlags = 280
            TopColumn = 0
         End
      End
   End
   Begin SQLPane = 
   End
   Begin DataPane = 
      Begin ParameterDefaults = ""
      End
      RowHeights = 240
      Begin ColumnWidths = 10
         Width = 284
         Width = 1440
         Width = 1440
         Width = 1440
         Width = 1440
         Width = 1440
         Width = 1440
         Width = 1440
         Width = 1440
         Width = 1440
      End
   End
   Begin CriteriaPane = 
      Begin ColumnWidths = 11
         Column = 1440
         Alias = 900
         Table = 1170
         Output = 720
         Append = 1400
         NewValue = 1170
         SortType = 1350
         SortOrder = 1410
         GroupBy = 1350
         Filter = 1350
         Or = 1350
         Or = 1350
         Or ' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'VIEW',@level1name=N'V_AmMaintenance'


EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPane2', @value=N'= 1350
      End
   End
End
' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'VIEW',@level1name=N'V_AmMaintenance'


EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPaneCount', @value=2 , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'VIEW',@level1name=N'V_AmMaintenance' ******/
 

 
 

/****** Object:  View [dbo].[V_AmMeter]    Script Date: 02/21/2014 16:37:10 ******/
 
 

 
 


CREATE or replace  VIEW  V_AmMeter 
AS
SELECT a.*, p.Partment_Name   PartmentName, p.Partment_Phone   PartmentPhone, 
      r.Price_Value   Price, e.DictionaryValue_Value   UseAmFX, 
      x.DictionaryValue_Value   UseAmXZ,   q.Area_Phone   AreaPhone, 
      b.Architecture_Phone   ArchitecturePhone, 
      q.Area_Name  || '.'  || b.Architecture_Name  || '.F'  || TO_CHAR( a.Floor) 
       || '.'  || a.AmMeter_Name   Meter_Name, p.Partment_Text   FullPartment
FROM  AmMeter a LEFT OUTER JOIN
       Partment p ON p.Partment_ID = a.Partment LEFT OUTER JOIN
       Price r ON r.Price_ID = a.Price_ID LEFT OUTER JOIN
          (SELECT DictionaryValue_Num, DictionaryValue_Value
         FROM DictionaryValue
         WHERE Dictionary_ID = 7) e ON 
      e.DictionaryValue_Num = SUBSTR(a.AmMeter_Num, 14, 1) LEFT OUTER JOIN
          (SELECT DictionaryValue_Num, DictionaryValue_Value
         FROM DictionaryValue
         WHERE Dictionary_ID = 6) x ON 
      x.DictionaryValue_Num = a.UseAmStyle LEFT OUTER JOIN
          (SELECT MeteStyle_ID, TO_CHAR( TexingValue) bl
         FROM TexingValue
         WHERE MeterTexing_ID = 8) t ON 
      t.MeteStyle_ID = a.MeteStyle_ID LEFT OUTER JOIN
       Architecture b ON b.Architecture_ID = a.Architecture_ID LEFT OUTER JOIN
       Area q ON q.Area_ID = a.Area_ID;


 

/****** EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPane1', @value=N'[0E232FF0-B466-11cf-A24F-00AA00A3EFFF, 1.00]
Begin DesignProperties = 
   Begin PaneConfigurations = 
      Begin PaneConfiguration = 0
         NumPanes = 4
         Configuration = "(H (1[22] 4[39] 2[20] 3) )"
      End
      Begin PaneConfiguration = 1
         NumPanes = 3
         Configuration = "(H (1 [50] 4 [25] 3))"
      End
      Begin PaneConfiguration = 2
         NumPanes = 3
         Configuration = "(H (1[50] 2[25] 3) )"
      End
      Begin PaneConfiguration = 3
         NumPanes = 3
         Configuration = "(H (4 [30] 2 [40] 3))"
      End
      Begin PaneConfiguration = 4
         NumPanes = 2
         Configuration = "(H (1 [56] 3))"
      End
      Begin PaneConfiguration = 5
         NumPanes = 2
         Configuration = "(H (2 [66] 3))"
      End
      Begin PaneConfiguration = 6
         NumPanes = 2
         Configuration = "(H (4 [50] 3))"
      End
      Begin PaneConfiguration = 7
         NumPanes = 1
         Configuration = "(V (3))"
      End
      Begin PaneConfiguration = 8
         NumPanes = 3
         Configuration = "(H (1 [56] 4 [18] 2))"
      End
      Begin PaneConfiguration = 9
         NumPanes = 2
         Configuration = "(H (1 [75] 4))"
      End
      Begin PaneConfiguration = 10
         NumPanes = 2
         Configuration = "(H (1[66] 2) )"
      End
      Begin PaneConfiguration = 11
         NumPanes = 2
         Configuration = "(H (4 [60] 2))"
      End
      Begin PaneConfiguration = 12
         NumPanes = 1
         Configuration = "(H (1) )"
      End
      Begin PaneConfiguration = 13
         NumPanes = 1
         Configuration = "(V (4))"
      End
      Begin PaneConfiguration = 14
         NumPanes = 1
         Configuration = "(V (2))"
      End
      ActivePaneConfig = 0
   End
   Begin DiagramPane = 
      Begin Origin = 
         Top = 0
         Left = 0
      End
      Begin Tables = 
         Begin Table = "a"
            Begin Extent = 
               Top = 6
               Left = 38
               Bottom = 109
               Right = 226
            End
            DisplayFlags = 280
            TopColumn = 30
         End
         Begin Table = "p"
            Begin Extent = 
               Top = 6
               Left = 264
               Bottom = 109
               Right = 434
            End
            DisplayFlags = 280
            TopColumn = 0
         End
         Begin Table = "r"
            Begin Extent = 
               Top = 6
               Left = 472
               Bottom = 109
               Right = 618
            End
            DisplayFlags = 280
            TopColumn = 0
         End
         Begin Table = "e"
            Begin Extent = 
               Top = 6
               Left = 656
               Bottom = 81
               Right = 862
            End
            DisplayFlags = 280
            TopColumn = 0
         End
         Begin Table = "x"
            Begin Extent = 
               Top = 6
               Left = 900
               Bottom = 81
               Right = 1106
            End
            DisplayFlags = 280
            TopColumn = 0
         End
         Begin Table = "t"
            Begin Extent = 
               Top = 6
               Left = 1144
               Bottom = 81
               Right = 1296
            End
            DisplayFlags = 280
            TopColumn = 0
         End
         Begin Table = "b"
            Begin Extent = 
               Top = 84
               Left = 656
               Bottom = 187
               Right = 898
            End
            DisplayFlags = 280
            TopColumn = 0
         End
      ' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'VIEW',@level1name=N'V_AmMeter'
 

EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPane2', @value=N'   Begin Table = "q"
            Begin Extent = 
               Top = 84
               Left = 936
               Bottom = 187
               Right = 1088
            End
            DisplayFlags = 280
            TopColumn = 0
         End
      End
   End
   Begin SQLPane = 
   End
   Begin DataPane = 
      Begin ParameterDefaults = ""
      End
      RowHeights = 240
      Begin ColumnWidths = 44
         Width = 284
         Width = 1440
         Width = 1440
         Width = 1440
         Width = 1440
         Width = 1440
         Width = 1440
         Width = 1440
         Width = 1440
         Width = 1440
         Width = 1440
         Width = 1440
         Width = 1440
         Width = 1440
         Width = 1440
         Width = 1440
         Width = 1440
         Width = 1440
         Width = 1440
         Width = 1440
         Width = 1440
         Width = 1440
         Width = 1440
         Width = 1440
         Width = 1440
         Width = 1440
         Width = 1440
         Width = 1440
         Width = 1440
         Width = 1440
         Width = 1440
         Width = 1440
         Width = 1440
         Width = 1440
         Width = 1440
         Width = 1440
         Width = 1440
         Width = 1440
         Width = 1440
         Width = 1440
         Width = 1440
         Width = 1440
         Width = 1440
         Width = 1440
      End
   End
   Begin CriteriaPane = 
      Begin ColumnWidths = 11
         Column = 1440
         Alias = 900
         Table = 1170
         Output = 720
         Append = 1400
         NewValue = 1170
         SortType = 1350
         SortOrder = 1410
         GroupBy = 1350
         Filter = 1350
         Or = 1350
         Or = 1350
         Or = 1350
      End
   End
End
' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'VIEW',@level1name=N'V_AmMeter'
 

EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPaneCount', @value=2 , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'VIEW',@level1name=N'V_AmMeter' ******/
 

 
 

/****** Object:  View [dbo].[V_ArcDayEnergy]    Script Date: 02/21/2014 17:02:13 ******/
 
 

CREATE or replace View V_ArcDayEnergy
/*
建筑能源每日汇总视图
*/
as
select e.Architecture_ID  ,e.Area_ID,
nvl(a.zgross,0) am_gross,
nvl(a.zmoney,0) am_money,
nvl(b.zgross,0) water_gross,
nvl(b.zmoney,0) water_money,
nvl(c.zgross,0) gas_gross,
nvl(c.zmoney,0) gas_money,
nvl(d.En07,0) En07,
nvl(d.En10,0) En10,
nvl(d.En11,0) En11,
nvl(d.En12,0) En12,
nvl(a.SelectMonth,nvl(b.SelectMonth,nvl(c.SelectMonth,m_month)))   SelectMonth,
nvl(a.Selectyear,nvl(b.Selectyear,nvl(c.Selectyear,m_year)))   Selectyear,
nvl(a.SelectDay,nvl(b.SelectDay,nvl(c.SelectDay,m_day)))   SelectDay
from architecture  e join 
T_arcDayAm a on a.architecture_id=e.architecture_id 
left join 
T_arcDayWater  b on a.architecture_id=b.architecture_id and a.Selectmonth=b.Selectmonth and a.Selectyear=b.Selectyear and a.SelectDay=b.SelectDay
left join 
T_arcDayGas c on b.architecture_id=c.architecture_id and b.Selectmonth=c.Selectmonth and b.Selectyear=c.Selectyear and b.SelectDay=c.SelectDay
left join  
(select architecture_id,to_number(Manual_insertTime,'120')  M_year,to_number(Manual_insertTime,'120')  M_month ,to_number(Manual_insertTime,'120')  M_day,
sum(En07)  En07, sum(En10)  En10, sum(En11)  En11, sum(En12)  En12 from ManualDay  GROUP BY architecture_id, to_number(Manual_insertTime,'120'), to_number(Manual_insertTime,'120'), to_number(Manual_insertTime,'120'))  d 
on c.architecture_id=d.architecture_id and c.Selectmonth=d.m_month and c.Selectyear=d.m_year and c.SelectDay=d.m_Day;

/*
 (select architecture_id,year(convert(date,Manual_insertTime,120)) as M_year,month(convert(date,Manual_insertTime,120)) as M_month ,
day(convert(date,Manual_insertTime,120)) as M_day,
sum(En07) as En07, sum(En08) as En08, sum(En09) as En09, sum(En10) as En10
from ManualDay 
group by architecture_id,Manual_insertTime ) as d on c.architecture_id=d.architecture_id and c.Selectmonth=d.m_month and c.Selectyear=d.m_year and c.SelectDay=d.m_Day
GO
 */


 

/****** Object:  View [dbo].[V_ArcMonthEnergy]    Script Date: 02/21/2014 17:12:05 ******/
 
 
 






CREATE or replace       View  V_ArcMonthEnergy 
/*
建筑能源月度汇总视图
*/
as
select e.Architecture_ID  ,e.Area_ID,
nvl(a.am_gross,0) am_gross,
nvl(a.am_money,0) am_money,
nvl(b.water_gross,0) water_gross,
nvl(b.water_money,0) water_money,
nvl(c.gas_gross,0) gas_gross,
nvl(c.gas_money,0) gas_money,
nvl(d.En07,0) En07,
nvl(d.En10,0) En10,
nvl(d.En11,0) En11,
nvl(d.En12,0) En12,
nvl(am_month,nvl(water_month,nvl(gas_month,m_month)))   SelectMonth,
nvl(am_year,nvl(water_year,nvl(gas_year,m_year)))    Selectyear
from architecture   e inner join
(select architecture_id,sum(zgross)   am_gross,sum(zmoney)   am_money,
/* cast(SelectYear as varchar(4))+'-'
+case when len(cast(selectMonth as varchar(2)))>1 then cast(selectMonth as varchar(2)) else '0'+cast(selectMonth as varchar(2)) end */
SelectYear   am_Year,selectMonth   am_Month
from T_arcDayAm 
group by architecture_id,SelectYear,selectMonth )   a on a.architecture_id=e.architecture_id 
left join 
(select architecture_id,sum(zgross)   water_gross,sum(zmoney)   water_money,
/*
cast(SelectYear as varchar(4))+'-'
+case when len(cast(selectMonth as varchar(2)))>1 then cast(selectMonth as varchar(2)) else '0'+cast(selectMonth as varchar(2)) end as water_month
*/
SelectYear   water_Year,selectMonth   water_Month
from T_arcDayWater 
group by architecture_id,SelectYear,selectMonth)   b on a.architecture_id=b.architecture_id and a.am_month=b.water_month and a.am_year=b.water_year
left join
(select architecture_id,sum(zgross)   gas_gross,sum(zmoney)   gas_money,
/*
cast(SelectYear as varchar(4))+'-'
+case when len(cast(selectMonth as varchar(2)))>1 then cast(selectMonth as varchar(2)) else '0'+cast(selectMonth as varchar(2)) end as gas_month
*/
SelectYear   gas_Year,selectMonth   gas_Month
from T_arcDayGas 
group by architecture_id,SelectYear,selectMonth)   c on b.architecture_id=c.architecture_id and b.water_month=c.gas_month and b.water_year=c.gas_year
left join 
(select architecture_id,selectyear   M_year,selectmonth   M_month ,sum(En07)   En07, sum(En10)   En10, sum(En11)   En11, sum(En12)   En12
from ManualMonth 
GROUP BY architecture_id, selectyear, selectmonth)   d on c.architecture_id=d.architecture_id and c.gas_month=d.m_month and c.gas_year=d.m_year;



/****** Object:  View [dbo].[V_GatherToDatasite]    Script Date: 02/21/2014 17:22:07 ******/
 
 

 
 

CREATE or replace VIEW  V_GatherToDatasite 
AS
SELECT  DataSite.DataSite_ID,  DataSite.DataSite_IP,  Gather.Gather_Address, 
       DataSite.DataSite_State
FROM  DataSite INNER JOIN
       Gather ON  DataSite.DataSite_ID =  Gather.DataSite_ID;

 
 
 

/****** Object:  View [dbo].[V_Gross]    Script Date: 02/21/2014 17:23:58 ******/
/*
IF  EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N' V_Gross '))
DROP VIEW  V_Gross
*/
 

 
 

/****** Object:  View [dbo].[V_Gross]    Script Date: 02/21/2014 17:23:58 ******/
 
 
 
 

/*
CREATE or replace    view  V_Gross 
as
select A.SelectTime,nvl(AmZGross,0)AmZGross,nvl(WaterZGross,0)WaterZGross,nvl(GasZGross,0)GasZGross from 
(select A.SelectTime,AmZGross,WaterZGross from 
(select sum(ZGross) AmZGross,SelectTime from (select ZGross,substr(SelectTime,6,2)SelectTime from T_MonthAm where subst(SelectTime,1,4)='2010')T group by SelectTime)A Left join
(select sum(ZGross) WaterZGross,SelectTime from (select ZGross,substr(SelectTime,6,2)SelectTime from T_MonthWater where substr(SelectTime,1,4)='2010')T group by SelectTime)B 
on A.SelectTime=B.SelectTime)A Left join
(select sum(ZGross) GasZGross,SelectTime from (select ZGross,substr(SelectTime,6,2)SelectTime from T_MonthGas where substr(SelectTime,1,4)='2010')T group by SelectTime)B
on  A.SelectTime=B.SelectTime;
*/

 









 
 

/****** Object:  View [dbo].[V_LineInfo]    Script Date: 02/21/2014 17:32:02 ******/
 
 

 
 
/*
CREATE or replace  view  V_LineInfo     找不到line表，无法实现查找 
as
select d.Line_ID,e.Line_Name   ParentLine,d.Transformer_Name,d.Line_Num,d.Line_Name,d.VoltageLevel,d.Line_Length,d.Line_FromPoint,d.Line_EndPoint,d.Line_Remark from
(select c.Line_ID,c.Line_ParentLineId,Transformer.Transformer_Name,c.Line_Num,c.Line_Name,c.VoltageLevel,c.Line_Length,c.Line_FromPoint,c.Line_EndPoint,c.Line_Remark   
from (select Line_ID,Line_ParentLineId,Transformer_ID,Line_Num,Line_Name,b.DictionaryValue_Value   VoltageLevel,Line_Length,Line_FromPoint,Line_EndPoint,Line_Remark from Line   a left join 
(select DictionaryValue_Num,DictionaryValue_Value from DictionaryValue  where Dictionary_ID=18)   b on a.VoltageLevel_ID=b.DictionaryValue_Num) c left join
Transformer on c.Transformer_ID=Transformer.Transformer_ID) d left join Line   e on d.Line_ParentLineId=e.Line_ID
*/


 

/****** Object:  View [dbo].[V_MessageInfo]    Script Date: 02/21/2014 17:36:08 ******/
 

 

/*
CREATE or replace  view  V_MessageInfo 
as
select Message_ID,Message_Title,Message_Time,NVL((select Users_Name from Users   b where a.Users_ID=b.Users_ID),'系统') Users_Name,Message_IsTop from Message   a;
*/

 
/****** Object:  View [dbo].[V_MeterToGather]    Script Date: 02/21/2014 17:40:17 ******/
 

CREATE or replace VIEW  V_MeterToGather 
AS
SELECT  Gather.Gather_ID,  Gather.Gather_Address,  AmMeter.AmMeter_ID, 
       AmMeter.AmMeter_485Address
FROM  AmMeter INNER JOIN
      Gather ON  AmMeter.Gather_ID =  Gather.Gather_ID;

 

/****** Object:  View V_ModuleToPower    Script Date: 02/20/2014 10:51:52 ******/




CREATE or replace  VIEW V_ModuleToPower
 as
SELECT     a.Power_ID, b.Module_ID, b.Module_Name, b.Module_Num, b.Module_Parent, b.Module_Address, b.Module_Part1, b.Module_Image,Module_Order
FROM          Power   a INNER JOIN
                       Module   b ON a.Module_ID = b.Module_ID
WHERE     (a.Power_Level = 0);

/****** EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPane1', @value=N'[0E232FF0-B466-11cf-A24F-00AA00A3EFFF, 1.00
Begin DesignProperties = 
   Begin PaneConfigurations = 
      Begin PaneConfiguration = 0
         NumPanes = 4
         Configuration = "(H (1[40 4[20 2[20 3) )"
      End
      Begin PaneConfiguration = 1
         NumPanes = 3
         Configuration = "(H (1 [50 4 [25 3))"
      End
      Begin PaneConfiguration = 2
         NumPanes = 3
         Configuration = "(H (1 [50 2 [25 3))"
      End
      Begin PaneConfiguration = 3
         NumPanes = 3
         Configuration = "(H (4 [30 2 [40 3))"
      End
      Begin PaneConfiguration = 4
         NumPanes = 2
         Configuration = "(H (1 [56 3))"
      End
      Begin PaneConfiguration = 5
         NumPanes = 2
         Configuration = "(H (2 [66 3))"
      End
      Begin PaneConfiguration = 6
         NumPanes = 2
         Configuration = "(H (4 [50 3))"
      End
      Begin PaneConfiguration = 7
         NumPanes = 1
         Configuration = "(V (3))"
      End
      Begin PaneConfiguration = 8
         NumPanes = 3
         Configuration = "(H (1[56 4[18 2) )"
      End
      Begin PaneConfiguration = 9
         NumPanes = 2
         Configuration = "(H (1 [75 4))"
      End
      Begin PaneConfiguration = 10
         NumPanes = 2
         Configuration = "(H (1[66 2) )"
      End
      Begin PaneConfiguration = 11
         NumPanes = 2
         Configuration = "(H (4 [60 2))"
      End
      Begin PaneConfiguration = 12
         NumPanes = 1
         Configuration = "(H (1) )"
      End
      Begin PaneConfiguration = 13
         NumPanes = 1
         Configuration = "(V (4))"
      End
      Begin PaneConfiguration = 14
         NumPanes = 1
         Configuration = "(V (2))"
      End
      ActivePaneConfig = 0
   End
   Begin DiagramPane = 
      Begin Origin = 
         Top = 0
         Left = 0
      End
      Begin Tables = 
         Begin Table = "a"
            Begin Extent = 
               Top = 6
               Left = 38
               Bottom = 177
               Right = 220
            End
            DisplayFlags = 280
            TopColumn = 0
         End
         Begin Table = "b"
            Begin Extent = 
               Top = 6
               Left = 237
               Bottom = 147
               Right = 555
            End
            DisplayFlags = 280
            TopColumn = 0
         End
      End
   End
   Begin SQLPane = 
   End
   Begin DataPane = 
      Begin ParameterDefaults = ""
      End
   End
   Begin CriteriaPane = 
      Begin ColumnWidths = 11
         Column = 1440
         Ali  = 900
         Table = 1170
         Output = 720
         Append = 1400
         NewValue = 1170
         SortType = 1350
         SortOrder = 1410
         GroupBy = 1350
         Filter = 1350
         Or = 1350
         Or = 1350
         Or = 1350
      End
   End
End
' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'VIEW',@level1name=N'V_ModuleToPower'

EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPaneCount', @value=1 , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'VIEW',@level1name=N'V_ModuleToPower'******/






