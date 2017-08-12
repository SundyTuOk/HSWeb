package com.sf.energy.project.run.model;

import javax.servlet.http.HttpSession;

public class LogInfoModel
{
		///日志ID
		int systemLogID;
	
		///操作员
		String userName;
		
		///操作员ID
		int userID;
		
		///操作时间
		String operationTime;
		
		///操作信息
		String operationKeyWord;
		
		///操作结果
		int operationResult;
		
		//操作结果，1表示成功，其他表示失败
		String operationResultString;

		///相关模块
		String  moduleNum;
		
		///模块名称
		String moduleName;
		
		///模块ID
		String moduleID;
		
		///操作失败信息
		String logMessage;
		
		
		
		public String getOperationResultString()
		{
			return operationResultString;
		}

		public void setOperationResultString(String operationResultString)
		{
			this.operationResultString = operationResultString;
		}

		public int getSystemLogID()
		{
			return systemLogID;
		}

		public void setSystemLogID(int systemLogID)
		{
			this.systemLogID = systemLogID;
		}

		public String getLogMessage()
		{
			return logMessage;
		}

		public void setLogMessage(String logMessage)
		{
			this.logMessage = logMessage;
		}

		public int getOperationResult()
		{
			return operationResult;
		}

		public void setOperationResult(int operationResult)
		{
			this.operationResult = operationResult;
		}
		
		public String getUserName()
		{
			return userName;
		}

		public void setUserName(String userName)
		{
			this.userName = userName;
		}

		public int getUserID()
		{
			return userID;
		}

		public void setUserID(int userID)
		{
			this.userID = userID;
		}

		public String getOperationTime()
		{
			return operationTime;
		}

		public void setOperationTime(String operationTime)
		{
			this.operationTime = operationTime;
		}

		public String getOperationKeyWord()
		{
			return operationKeyWord;
		}

		public void setOperationKeyWord(String operationKeyWord)
		{
			this.operationKeyWord = operationKeyWord;
		}

		

		public String getModuleNum()
		{
			return moduleNum;
		}

		public void setModuleNum(String moduleNum)
		{
			this.moduleNum = moduleNum;
		}

		public String getModuleName()
		{
			return moduleName;
		}

		public void setModuleName(String moduleName)
		{
			this.moduleName = moduleName;
		}

		public String getModuleID()
		{
			return moduleID;
		}

		public void setModuleID(String moduleID)
		{
			this.moduleID = moduleID;
		}
}
