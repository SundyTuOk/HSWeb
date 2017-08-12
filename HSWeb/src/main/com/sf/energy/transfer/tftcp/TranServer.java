package com.sf.energy.transfer.tftcp;

import java.net.InetSocketAddress;
import java.net.URL;
import java.nio.charset.Charset;

import javax.swing.JTextArea;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSessionConfig;
import org.apache.mina.filter.buffer.BufferedWriteFilter;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LogLevel;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
/**
 * 中转站作为服务器和数据网关进行通信
 * @author 蒯越
 * @version 1.0
 * @see void start()
 */
public class TranServer {
	private static Logger logger = Logger.getLogger(TranServer.class);

	private  int port;
    public TranServer(int port){
    	this.port=port;
    }
    /**
     * 开启中转站服务器
     * @param jTextArea2 
     * @param null
     * @return null
     * 
     */
	public void start() {
		URL	url=ClassLoader.getSystemResource("log4j.properties");
		PropertyConfigurator.configure(url);
		IoAcceptor acceptor = null;
		try {
			/// 创建一个非阻塞的server端的Socket
			acceptor = new NioSocketAcceptor();
							
			/// 设置日志过滤器
			LoggingFilter lf = new LoggingFilter();
			lf.setMessageReceivedLogLevel(LogLevel.DEBUG);
			acceptor.getFilterChain().addLast("logger", lf); 
			///获得IoSessionConfig对象
			IoSessionConfig cfg = acceptor.getSessionConfig();
			/// 读写通道10秒内无操作进入空闲状态
			cfg.setIdleTime(IdleStatus.BOTH_IDLE, 10000);

			/// 绑定处理器
			acceptor.setHandler(new TranServerHandler());
			/// 绑定端口
			acceptor.bind(new InetSocketAddress(port));
			logger.info("服务端启动成功...     端口号为：" + port);
			//jTextArea2.append("服务端启动成功...     端口号为：" + port);
		} catch (Exception e) {
			logger.error("服务端启动异常....", e);
			e.printStackTrace();
		}
	}
}
