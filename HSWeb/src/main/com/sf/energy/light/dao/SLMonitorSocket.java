package com.sf.energy.light.dao;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import com.sf.energy.light.model.XMLCoder;

public class SLMonitorSocket {
	private static Logger logger = Logger.getLogger(SLMonitorSocket.class);
	private  String host;
	private  int port;
	public SLMonitorSocket(String host,int port){
		  this.host=host;
		  this.port=port;
	   }
	public void start(XMLCoder xmlCoder) throws IOException {
		
		String commondXML=null;
		commondXML="<SFROOT gy='GW'><GW identify='1' opercode='" 
				+xmlCoder.getOperCode()
				+"' tasknum='"
				+ xmlCoder.getTaskNum()
				+ "' terminaladdress='"
				+ xmlCoder.getTerminalAddress()
				+ "' way='" 
				+xmlCoder.getWay()
				+"'><command code='" 
				+xmlCoder.getCode()	
				+"' pw='" 
				+xmlCoder.getPw()
				+"' para='" 
				+xmlCoder.getPara()
				+"'></command></GW></SFROOT>\r\n";	
	
		PropertyConfigurator.configure("configuration//log4j.properties");
	
	
		// 创建一个非阻塞的客户端程序
		IoConnector connector = new NioSocketConnector();  // 创建连接
		// 设置链接超时时间
		//connector.setConnectTimeout(30000);
		// 添加过滤器
		TextLineCodecFactory textLineCodecFactory=new TextLineCodecFactory(Charset
				.forName("UTF-8"), LineDelimiter.WINDOWS.getValue(),
				LineDelimiter.WINDOWS.getValue());
		textLineCodecFactory.setDecoderMaxLineLength(44098);
		textLineCodecFactory.setEncoderMaxLineLength(44098);
		//ProtocolCodecFilter protocolCodecFilter=new ProtocolCodecFilter(new CodecFactory());
		ProtocolCodecFilter protocolCodecFilter=new ProtocolCodecFilter(textLineCodecFactory);
		connector.getFilterChain().addLast(   //添加消息过滤器
				"codec",protocolCodecFilter); 
		logger.info("客户端收到信息");
		// 设置读取数据的缓冲区大小
		//connector.getSessionConfig().setReadBufferSize(4096);
		// 添加业务逻辑处理器类
		connector.setHandler(new SLMonitorReply());// 添加业务处理
		IoSession ioSession = null;
		try {
			ConnectFuture future = connector.connect(new InetSocketAddress(
					host, port));// 创建连接
			future.awaitUninterruptibly();// 等待连接创建完成
			ioSession = future.getSession();// 获得session
			
			ioSession.write(commondXML);			
		} catch (Exception e) {
			logger.error("客户端链接异常...", e);
		}

		//ioSession.getCloseFuture().awaitUninterruptibly();// 等待连接断开
		//connector.dispose();
	}
}
