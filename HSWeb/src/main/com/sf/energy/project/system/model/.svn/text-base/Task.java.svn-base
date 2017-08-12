package com.sf.energy.project.system.model;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * <计划任务类>
 * <设置下次执行时间>
 * @author lujinquan
 * @version v1.0
 * @since [盛帆电子/服务器]
 */
public class Task
{
	// / 任务ID
	private int planTask_ID = 0;

	// / 任务类型
	private String planTask_Style = "";

	// / 执行类型;定时运行,周期运行
	private String planTask_Exec = "";

	// / 下次执行时间
	private Date planTask_NextTime = new Date();

	// / 上次执行时间
	private String planTask_LastTime = "";

	// / 上次执行状态
	private String planTask_LastState = "";

	// / 定时运行时间
	private String planTask_01Time = "";

	// / 周期运行类型 D-每天执行，M-每月执行
	private String planTask_02Pinlv = "D";

	// / 周期运行间隔天数 ，条件：planTask_02Pinlv = "D"
	private int planTask_02PinlvD = 0;

	// / 周期运行执行天数 ，条件：planTask_02Pinlv = "M"
	private int planTask_02PinlvMday = 0;

	// / 周期运行执行月数 ，条件：planTask_02Pinlv = "M"
	private int planTask_02PinlvMmonth = 0;

	// / 周期运行发生类型 ，1-一次发生于，0-发生周期
	private int planTask_02PinlvStyle = 0;

	// / 周期运行发生时间为：一次发生于的发生时间条件（planTask_02PinlvStyle=1）
	private String planTask_02PinlvTime = "";

	// / 周期运行发生周期触发时间间隔
	private int planTask_02Zhouqi = 0;

	// / 周期运行发生周期触发时间类型 ，"Hour"-小时发生，"Minute"-分钟发生
	private String planTask_02ZhouqiStyle = "";

	// / 任务当前状态0未执行，1执行完毕
	private int execState = 0;

	// / 是否正在执行
	private boolean isExec = true;

	public int getPlanTask_ID()
	{
		return planTask_ID;
	}

	public void setPlanTask_ID(int planTask_ID)
	{
		this.planTask_ID = planTask_ID;
	}

	public String getPlanTask_Style()
	{
		return planTask_Style;
	}

	public void setPlanTask_Style(String planTask_Style)
	{
		this.planTask_Style = planTask_Style;
	}

	public Date getPlanTask_NextTime()
	{
		return planTask_NextTime;
	}

	public void setPlanTask_NextTime(Date planTask_NextTime)
	{
		this.planTask_NextTime = planTask_NextTime;
	}

	public int getExecState()
	{
		return execState;
	}

	public void setExecState(int execState)
	{
		this.execState = execState;
	}

	public boolean isExec()
	{
		return isExec;
	}

	public void setExec(boolean isExec)
	{
		this.isExec = isExec;
	}

	// /定义时间格式
	SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 构造函数
	 * 
	 * @param data
	 *            plantask表里的数据
	 * @throws ParseException
	 */
	public Task(ArrayList<String> data) throws ParseException
	{
		planTask_ID = Integer.parseInt(data.get(0));
		planTask_Style = data.get(1);
		planTask_LastTime = data.get(2);
		planTask_LastState = data.get(3);
		planTask_Exec = data.get(4);
		planTask_01Time = data.get(5);
		planTask_02Pinlv = data.get(6);
		planTask_02PinlvD = Integer.parseInt(data.get(7));
		planTask_02PinlvMday = Integer.parseInt(data.get(8));
		planTask_02PinlvMmonth = Integer.parseInt(data.get(9));
		planTask_02PinlvStyle = Integer.parseInt(data.get(10));
		planTask_02PinlvTime = data.get(11);
		planTask_02Zhouqi = Integer.parseInt(data.get(12));
		planTask_02ZhouqiStyle = data.get(13);

		Date now = new Date();
		// 计算下次执行时间
		if (planTask_Exec.equals("定时运行"))
		{
			planTask_NextTime = df1.parse(planTask_01Time);

			if (planTask_NextTime.before(now))
			{
				isExec = false;
			} else
			{
				isExec = true;
			}
		} else if (planTask_Exec.equals("周期运行"))
		{
			isExec = true;

			// 每天发生
			if (planTask_02Pinlv.equals("D"))
			{
				
				// 一次发生
				if (planTask_02PinlvStyle == 1)
				{
					if (planTask_LastTime != null && !"".equals(planTask_LastTime))
					{
						// 将上次执行时间转换成Date型
						Date dtLast = df1.parse(planTask_LastTime);
						// 将上次执行时间取年月日再加上planTask_02PinlvTime赋值给下次执行时间
						planTask_NextTime = df1.parse(df2.format(dtLast) + " "
								+ planTask_02PinlvTime);
					} else
					{
						// 将系统当前时间取年月日再加上planTask_02PinlvTime赋值给下次执行时间
						planTask_NextTime = df1.parse(df2.format(now) + " "
								+ planTask_02PinlvTime);
					}

					while (planTask_NextTime.before(now))
					{
						// 如果下次执行时间小于当前时间，则加上周期运行间隔天数
						planTask_NextTime = addDate(planTask_NextTime,
								planTask_02PinlvD);
					}
				}
				// 周期发生
				else
				{
					if (planTask_LastTime != null && !"".equals(planTask_LastTime))
					{
						// 将上次执行时间转换成Date型
						Date dtLast = df1.parse(planTask_LastTime);
						// 当天
						if (df2.format(dtLast).equals(df2.format(now)))
						{
							// 下次执行时间先赋值为上次执行时间，取时间格式yyyy-MM-dd HH:mm:ss
							planTask_NextTime = dtLast;
							while (planTask_NextTime.before(now))
							{
								if (planTask_02ZhouqiStyle.equals("Hour"))
								{
									// 将下次执行时间加上周期时间
									planTask_NextTime = addHour(
											planTask_NextTime,
											planTask_02Zhouqi);
								} else if (planTask_02ZhouqiStyle
										.equals("Minute"))
								{
									// 将下次执行时间加上周期时间
									planTask_NextTime = addMinutes(
											planTask_NextTime,
											planTask_02Zhouqi);
								}
							}
						} else
						{
							planTask_NextTime = dtLast;
							while (planTask_NextTime.before(now))
							{
								planTask_NextTime = addDate(planTask_NextTime,
										planTask_02PinlvD);
							}
							// 当天执行
							if (df2.format(planTask_NextTime).equals(
									df2.format(now)))
							{
								planTask_NextTime = df1.parse(df1.format(now));
							}
						}
					}
				}
			}
			// 每月发生
			else if (planTask_02Pinlv.equals("M"))
			{
				
				// 一次发生于
				if (planTask_02PinlvStyle == 1)
				{
					// 定义时间格式
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-"
							+ String.valueOf(planTask_02PinlvMday) + " "
							+ planTask_02PinlvTime);
					if (planTask_LastTime != null && !"".equals(planTask_LastTime))
					{
						// 将planTask_LastTime转换成yyyy-MM-dd HH:mm:ss格式的Date型变量
						Date dtLast = df1.parse(planTask_LastTime);
						planTask_NextTime = df.parse(df.format(dtLast));
					} else
					{
						planTask_NextTime = df.parse(df.format(now));
					}

					while (planTask_NextTime.before(now))
					{
						planTask_NextTime = addMonth(planTask_NextTime,
								planTask_02PinlvMmonth);
					}
				}
				// 周期发生于
				else
				{
					if (planTask_LastTime != null && !"".equals(planTask_LastTime))
					{
						// 将planTask_LastTime转换成yyyy-MM-dd HH:mm:ss格式的Date型变量
						Date dtLast = df1.parse(planTask_LastTime);
						// 定义时间格式df
						SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-"
								+ String.valueOf(planTask_02PinlvMday));

						// 当天
						if (df.format(dtLast).equals(df2.format(now)))
						{
							planTask_NextTime = dtLast;
							while (planTask_NextTime.before(now))
							{

								if (planTask_02ZhouqiStyle.equals("Hour"))
								{
									planTask_NextTime = addHour(
											planTask_NextTime,
											planTask_02Zhouqi);
								} else if (planTask_02ZhouqiStyle
										.equals("Minute"))
								{
									planTask_NextTime = addMinutes(
											planTask_NextTime,
											planTask_02Zhouqi);
								}
							}
						} else
						{
							planTask_NextTime = dtLast;
							while (planTask_NextTime.before(now))
							{
								planTask_NextTime = addMonth(planTask_NextTime,
										planTask_02PinlvMmonth);
							}
							// 当天执行
							if (df2.format(planTask_NextTime).equals(
									df2.format(now)))
							{
								planTask_NextTime = df1.parse(df1.format(now));
							}
						}
					}
				}
			}
		} else
		{
			isExec = false;
		}
	}

	/**
	 * 日期增加n月。
	 * 
	 * @param date
	 *            日期
	 * @param months
	 *            增加的月数
	 * @return 增加后的时间
	 * @throws ParseException
	 */
	private Date addMonth(Date date, int months) throws ParseException
	{
		Calendar cld = Calendar.getInstance();
		cld.setTime(date);
		cld.add(Calendar.MONTH, months);
		return cld.getTime();
	}

	/**
	 * 日期增加n天。
	 * 
	 * @param date
	 *            日期
	 * @param days
	 *            增加的天数
	 * @return 增加后的时间
	 * @throws ParseException
	 */
	private Date addDate(Date date, int days) throws ParseException
	{
		Calendar cld = Calendar.getInstance();
		cld.setTime(date);
		cld.add(Calendar.DATE, days);
		return cld.getTime();
	}

	/**
	 * 时间增加N小时
	 * 
	 * @param date
	 *            时间
	 * @param hours
	 *            增加的小时数
	 * @return 增加后的时间
	 * @throws ParseException
	 */
	private Date addHour(Date date, int hours) throws ParseException
	{
		Calendar cld = Calendar.getInstance();
		cld.setTime(date);
		cld.add(Calendar.HOUR_OF_DAY, hours);
		return cld.getTime();
	}

	/**
	 * 时间增加N分钟
	 * 
	 * @param date
	 *            时间
	 * @param minutes
	 *            增加的分钟数
	 * @return 增加后的时间
	 * @throws ParseException
	 */
	private Date addMinutes(Date date, int minutes) throws ParseException
	{
		Calendar cld = Calendar.getInstance();
		cld.setTime(date);
		cld.add(Calendar.MINUTE, minutes);
		return cld.getTime();
	}

	public boolean reMoveNext() throws ParseException
	{
		Date now = new Date();
		
		planTask_LastTime = df1.format(planTask_NextTime);
		planTask_LastState = "1";
		// 计算下次执行时间
		if (planTask_Exec.equals("定时运行"))
		{
			planTask_NextTime = df1.parse(planTask_01Time);

			if (planTask_NextTime.before(now))
			{
				isExec = false;
			} else
			{
				isExec = true;
			}
		} else if (planTask_Exec.equals("周期运行"))
		{
			isExec = true;

			// 每天发生
			if (planTask_02Pinlv.equals("D"))
			{
				// 一次发生于
				if (planTask_02PinlvStyle == 1)
				{
					if (planTask_LastTime != null && !"".equals(planTask_LastTime))
					{
						Date dtLast = df1.parse(planTask_LastTime);

						planTask_NextTime = df1.parse(df2.format(dtLast) + " "
								+ planTask_02PinlvTime);
					} else
					{
						planTask_NextTime = df1.parse(df2.format(now) + " "
								+ planTask_02PinlvTime);
					}

					while (planTask_NextTime.before(now))
					{
						planTask_NextTime = addDate(planTask_NextTime,
								planTask_02PinlvD);
					}

				} else
				// 周期发生于
				{
					if (planTask_LastTime != null && !"".equals(planTask_LastTime))
					{
						Date dtLast = df1.parse(planTask_LastTime);
						// 当天
						if (df2.format(dtLast).equals(df2.format(now)))
						{
							planTask_NextTime = dtLast;
							while (planTask_NextTime.before(now))
							{
								if (planTask_02ZhouqiStyle.equals("Hour"))
								{
									planTask_NextTime = addHour(
											planTask_NextTime,
											planTask_02Zhouqi);
								} else if (planTask_02ZhouqiStyle
										.equals("Minute"))
								{
									planTask_NextTime = addMinutes(
											planTask_NextTime,
											planTask_02Zhouqi);
								}

							}
						} else
						{

							planTask_NextTime = dtLast;
							while (planTask_NextTime.before(now))
							{
								if (df2.format(planTask_NextTime).equals(
										df2.format(now)))
								{
									// 当天执行
									planTask_NextTime = df1.parse(df1
											.format(now));
									break;
								} else
								{
									planTask_NextTime = addDate(
											planTask_NextTime,
											planTask_02PinlvD);
								}
							}
						}
					}
				}
				execState = 0;
			} 
			// 每月发生
			else if (planTask_02Pinlv.equals("M"))
			{
				// 一次发生
				if (planTask_02PinlvStyle == 1)
				{
					// 定义时间格式
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-"
							+ String.valueOf(planTask_02PinlvMday) + " "
							+ planTask_02PinlvTime);
					if (planTask_LastTime != null && !"".equals(planTask_LastTime))
					{
						Date dtLast = df1.parse(planTask_LastTime);
						planTask_NextTime = df.parse(df.format(dtLast));
					} else
					{
						planTask_NextTime = df.parse(df.format(now));
					}

					while (planTask_NextTime.before(now))
					{
						planTask_NextTime = addMonth(planTask_NextTime,
								planTask_02PinlvMmonth);
					}
				} else
				// 周期发生于
				{
					if (planTask_LastTime != null && !"".equals(planTask_LastTime))
					{
						Date dtLast = df1.parse(planTask_LastTime);

						// 定义时间格式df
						SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-"
								+ String.valueOf(planTask_02PinlvMday));

						if (df.format(dtLast).equals(df2.format(now)))// 当天
						{
							planTask_NextTime = dtLast;
							while (planTask_NextTime.before(now))
							{

								if (planTask_02ZhouqiStyle.equals("Hour"))
								{
									planTask_NextTime = addHour(
											planTask_NextTime,
											planTask_02Zhouqi);
								} else if (planTask_02ZhouqiStyle
										.equals("Minute"))
								{
									planTask_NextTime = addMinutes(
											planTask_NextTime,
											planTask_02Zhouqi);
								}

							}
						} else
						{
							planTask_NextTime = df1.parse(df.format(dtLast) + " 00:00:00");
							while (planTask_NextTime.before(now))
							{
								// 当天执行
								if (df2.format(planTask_NextTime).equals(
										df2.format(now)))
								{
									planTask_NextTime = df1.parse(df1
											.format(now));
									break;
								} 
								else
								{
									planTask_NextTime = addMonth(
											planTask_NextTime,
											planTask_02PinlvMmonth);
								}
							}
						}
					}
				}
			}
		}
		return isExec;
	}
}
