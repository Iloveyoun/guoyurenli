package af.spring;
/*自定义异常处理的类，用来处理我们里面抛出的异常
 * 
 * */

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

// 当注册多个 异常处理器时，@Order决定顺序，Order越低的越先执行
@Order(-100)
public class AfExceptionResolver implements HandlerExceptionResolver
{

	@Override
	public ModelAndView resolveException(HttpServletRequest request
			, HttpServletResponse response
			, Object handler
			, Exception exception)
	{

		// 取得请求的路径
		String uri = request.getRequestURI();
		if(uri.endsWith(".do"))	// 人为规定，.do结尾为REST请求
		{
			// REST 出错处理
			Map<String,Object> model = new HashMap<>();
			View view = new AfRestError(exception);		// 创建应答显示数据，我们自己写的View
			System.err.println("访问" + request.getRequestURI() + " 发生错误, 错误信息:" + exception.getMessage());
			
			return new ModelAndView(view, model);	// 返回应答数据
		}
		else	// 其他为MVC请求
		{
			// MVC 处理
			Map<String,Object> model = new HashMap<>();
			model.put("message", exception.getMessage());	// 这个是取得出错的具体信息，放到Model里面
				
			StringWriter stringWriter = new StringWriter();
			exception.printStackTrace(new PrintWriter(stringWriter));
			model.put("detail", stringWriter.toString());
				
			return new ModelAndView("error", model);	// 返回应答模板（出错模板：template/error.html）
		}
		
		// return null; // 返回null表示此Resolver未能处理该异常，则继续按默认方式处理
	}

}
