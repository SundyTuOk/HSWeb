package com.sf.energy.server.tftcp;

import java.net.InetSocketAddress;
import java.net.URL;
import java.nio.charset.Charset;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import com.sf.energy.server.db.TimerToTask;


public class Server {
	private static Logger logger = Logger.getLogger(Server.class);

	private static int port;
	public Server(int port){
		this.port=port;
//		new TimerToTask();
	}

	public void start() {
		URL	url=ClassLoader.getSystemResource("log4j.properties");
		PropertyConfigurator.configure(url);
		IoAcceptor acceptor = null;   // 创建连接
		try {
			// 创建一个非阻塞的server端的Socket
			acceptor = new NioSocketAcceptor();
			// 设置过滤器（使用Mina提供的文本换行符编解码器）
			TextLineCodecFactory textLineCodecFactory=new TextLineCodecFactory(Charset
					.forName("UTF-8"),
					LineDelimiter.WINDOWS.getValue(),
					LineDelimiter.WINDOWS.getValue());
			textLineCodecFactory.setDecoderMaxLineLength(44098);
			textLineCodecFactory.setEncoderMaxLineLength(44098);
			acceptor.getFilterChain().addLast( //添加消息过滤器
					"codec",
					new ProtocolCodecFilter(textLineCodecFactory)); 
			// 设置读取数据的缓冲区大小
			//acceptor.getSessionConfig().setReadBufferSize(4096);
			// 读写通道10秒内无操作进入空闲状态
			acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 1000);
			// 绑定逻辑处理器
			acceptor.setHandler(new ServerHandler()); // 添加业务处理
			// 绑定端口
			acceptor.bind(new InetSocketAddress(port));
			////System.out.println("端口启动");
			logger.info("服务端启动成功...     端口号为：" + port);
		} catch (Exception e) {
			logger.error("服务端启动异常....", e);
			e.printStackTrace();
		}
	}
}
