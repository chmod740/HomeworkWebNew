package me.hupeng.homeworkweb.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author HUPENG FORM IMUDGES
 * @version 2.0.0
 * @since jdk1.7
 * Date 2016.01.19
 * URP����ϵͳͨ����
 * */

public class JwxtUtil {
	/**
	 * ���ò���
	 * HOST ����ϵͳ����IP ����jwxt.xxx.edu.cn ���� 111.123.123
	 * ZJH MM ���ڻ�ȡcookie���û�������
	 * */
	final private String HOST = "jwxt.imu.edu.cn";
	final private String ZJH = "0141120903";
	final private String MM = "0141120903";
	
	/**
	 * ѧ�� �� ����
	 * */
	private String stuNum;
	private String password;
	private String studentAllInfo;
	
	public String getStuNum() {
		return stuNum;
	}
	public void setStuNum(String stuNum) throws Exception {
		this.stuNum = stuNum;
		studentAllInfo = null;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * �ڲ���̬��ģ��
	 * ѧ����Ϣ��
	 * */
	public static class StudentInfo{
		/**
		 * ����
		 * �Ա�
		 * ����
		 * ���֤����
		 * רҵ
		 * �༶
		 * ���Գɼ�
		 * */
		private String name;
		private String sex;
		private String nativePlace;
		private String idCardNum;
		private String profession;
		private String className;
		private Map<String, String>testScores;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getSex() {
			return sex;
		}
		public void setSex(String sex) {
			this.sex = sex;
		}
		public String getNativePlace() {
			return nativePlace;
		}
		public void setNativePlace(String nativePlace) {
			this.nativePlace = nativePlace;
		}
		public String getIdCardNum() {
			return idCardNum;
		}
		public void setIdCardNum(String idCardNum) {
			this.idCardNum = idCardNum;
		}
		public String getProfession() {
			return profession;
		}
		public void setProfession(String profession) {
			this.profession = profession;
		}
		public String getClassName() {
			return className;
		}
		public void setClassName(String className) {
			this.className = className;
		}
		public Map<String, String> getTestScores() {
			return testScores;
		}
		public void setTestScores(Map<String, String> testScores) {
			this.testScores = testScores;
		}
	}
	
	
	/**
	 * ��̬�ڲ���ģ��
	 * �ɼ���Ϣ��
	 * */
	private static class CourseModel{
		private String course;
		private int point;
		private int grade;
		private String date;
		public String getCourse() {
			return course;
		}
		public void setCourse(String course) {
			this.course = course;
		}
		public int getPoint() {
			return point;
		}
		public void setPoint(int point) {
			this.point = point;
		}
		public int getGrade() {
			return grade;
		}
		public void setGrade(int grade) {
			this.grade = grade;
		}
		public String getDate() {
			return date;
		}
		public void setDate(String date) {
			this.date = date;
		}
	}
	
	/**
	 * ����ģ��
	 * Http������
	 * */
	static class HttpRequest {
		private static String cookie = "";
		
	    public static String getCookie() {
			return cookie;
		}

		public static void setCookie(String cookie) {
			HttpRequest.cookie = cookie;
		}

		/**
	     * ��ָ��URL����GET����������
	     * 
	     * @param url
	     *            ���������URL
	     * @param param
	     *            ����������������Ӧ���� name1=value1&name2=value2 ����ʽ��
	     * @return URL ������Զ����Դ����Ӧ���
	     */
	    public static String sendGet(String url, String param) {
	    	//System.out.println("get");
	        String result = "";
	        BufferedReader in = null;
	        
	        try {
	            String urlNameString = url + "?" + param;
	            URL realUrl = new URL(urlNameString);
	            // �򿪺�URL֮�������
	            URLConnection connection = realUrl.openConnection();
	            // ����ͨ�õ���������
	            connection.setRequestProperty("accept", "*/*");
	            connection.setRequestProperty("connection", "Keep-Alive");
	            connection.setRequestProperty("user-agent",
	                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
	            connection.setRequestProperty("Cookie", "JSESSIONID=" + cookie);
	            // ����ʵ�ʵ�����
	            connection.connect();
	            // ��ȡ������Ӧͷ�ֶ�
	            Map<String, List<String>> map = connection.getHeaderFields();
	            // �������е���Ӧͷ�ֶ�
//	            for (String key : map.keySet()) {
//	                System.out.println(key + "--->" + map.get(key));
//	            }
	            // ���� BufferedReader����������ȡURL����Ӧ
	            in = new BufferedReader(new InputStreamReader(
	                    connection.getInputStream(),"gbk"));
	            String line;
	            while ((line = in.readLine()) != null) {
	                result += line;
	            }
	            if (result.contains("�����µ�¼")) {
					cookie = new JwxtUtil().getCookie();
					//System.out.println(cookie);
					//return HttpRequest.sendGet(url, param);
				}
	        } catch (Exception e) {
	            System.out.println("����GET��������쳣��" + e);
	            e.printStackTrace();
	        }
	        // ʹ��finally�����ر�������
	        finally {
	            try {
	                if (in != null) {
	                    in.close();
	                }
	            } catch (Exception e2) {
	                e2.printStackTrace();
	            }
	        }
	        return result;
	    }

	    /**
	     * ��ָ�� URL ����POST����������
	     * 
	     * @param url
	     *            ��������� URL
	     * @param param
	     *            ����������������Ӧ���� name1=value1&name2=value2 ����ʽ��
	     * @return ������Զ����Դ����Ӧ���
	     */
	    public static String sendPost(String url, String param) {
	    	//System.out.println("post");
	        PrintWriter out = null;
	        BufferedReader in = null;
	        String result = "";
	        try {
	            URL realUrl = new URL(url);
	            // �򿪺�URL֮�������
	            URLConnection conn = realUrl.openConnection();
	            // ����ͨ�õ���������
	            conn.setRequestProperty("accept", "*/*");
	            conn.setRequestProperty("connection", "Keep-Alive");
	            conn.setRequestProperty("user-agent",
	                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
	            conn.setRequestProperty("Cookie", "JSESSIONID=" + cookie);
	            // ����POST�������������������
	            conn.setDoOutput(true);
	            conn.setDoInput(true);
	            // ��ȡURLConnection�����Ӧ�������
	            out = new PrintWriter(conn.getOutputStream());
	            // �����������
	            out.print(param);
	            // flush������Ļ���
	            out.flush();
	            // ����BufferedReader����������ȡURL����Ӧ
	            in = new BufferedReader(
	                    new InputStreamReader(conn.getInputStream(),"gbk"));
	            String line;
	            while ((line = in.readLine()) != null) {
	                result += line;
	            }
	            if (result.contains("500 Servlet Exception")) {
	            	cookie = new JwxtUtil().getCookie();
					//System.out.println(cookie);
					//return HttpRequest.sendPost(url, param);
				}
	        } catch (Exception e) {
	            System.out.println("���� POST ��������쳣��"+e);
	            e.printStackTrace();
	        }
	        //ʹ��finally�����ر��������������
	        finally{
	            try{
	                if(out!=null){
	                    out.close();
	                }
	                if(in!=null){
	                    in.close();
	                }
	            }
	            catch(IOException ex){
	                ex.printStackTrace();
	            }
	        }
	        return result;
	    }    
	}
	
	
	/**
	 * ����ģ��
	 * ��������ģ��
	 * @throws Exception 
	 * �������ļ�����������Ϣû��ʱ���׳��쳣
	 * */
	
	private String getConfigInfo(String param) throws Exception {
		String paramString = null;
		if (param.equals("host")) {
			return HOST;
		}
		else {
			if (param.equals("zjh")) {
				return ZJH;
			}
			else {
				if (param.equals("mm")) {
					return MM;
				}
			}
		}
		return paramString;
	}
	
	/**
	 * ����ģ��
	 * ��ȡ��Ч��Ϣ
	 * @throws Exception 
	 * �˴����Դ��������쳣���д���
	 * ע���뱣֤�������� host zjh mm ������������Ч��
	 * */
	private String getCookie() throws Exception {
		String set_cookiesString = null;
		String url = "http://" + getConfigInfo("host")
				+ "/loginAction.do";
		String param = "zjh=" + getConfigInfo("zjh") + "&mm="
				+ getConfigInfo("mm");
		BufferedReader in = null;
		try {
			String urlNameString = url + "?" + param;
			URL realUrl = new URL(urlNameString);
			// �򿪺�URL֮�������
			URLConnection connection = realUrl.openConnection();
			// ����ͨ�õ���������
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// connection.setRequestProperty("Cookie",
			// "JSESSIONID=bfajrqHj1f0-YgGeg5a9u");
			// ����ʵ�ʵ�����
			connection.connect();
			// ��ȡ������Ӧͷ�ֶ�
			@SuppressWarnings("unused")
			Map<String, List<String>> map = connection.getHeaderFields();
			// �������е���Ӧͷ�ֶ�
			for (String key : map.keySet()) {
				// System.out.println(key + "--->" + map.get(key));
				if (key != null && key.equals("Set-Cookie")) {
					// System.out.println(map.get(key));
					set_cookiesString = map.get(key).get(0);
					// System.out.println(set_cookiesString);
				}
			}
			in = new BufferedReader(new InputStreamReader(
	                    connection.getInputStream()));
	        String line;
	        String result = "";
	        while ((line = in.readLine()) != null) {
	        	result += line;
	        }
	        if (result.contains("URP �ۺϽ���ϵͳ - ��¼")) {
				//�û��� ���� ���벻��ȷ �׳��쳣
	        	throw new Exception("�û��� ���� ���� ��Ч");
			}
		} catch (Exception e) {
			System.out.println("����GET��������쳣��" + e);
			e.printStackTrace();
		}
		// ʹ��finally�����ر�������
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		if (set_cookiesString == null) {
			return null;
		} else {
			//System.out.println(set_cookiesString);
			String cookieString = null;
			Pattern pattern = Pattern.compile("[0-9a-zA-Z_-]{11,32}");//�˴�ƥ���SetCookie�ַ���
			Matcher matcher = pattern.matcher(set_cookiesString);
			if (matcher.find()) {
				cookieString = matcher.group();
			}
			return cookieString;
		}
	}
	
	/**
	 * ����ģ��
	 * ��֤�û� �û��� ����� ��Ч��,��������û����룬��Ҫ����stuNum,password
	 * @return
	 * true: ������ȷ
	 * false:�������
	 * */
	
	public boolean checkStuPasswd() throws Exception{
		String url = "http://" + getConfigInfo("host")
				+ "/loginAction.do";
		if (stuNum == null || password == null) {
			throw new Exception("��������ѧ�ź�����");
		}
		String param = "zjh=" + stuNum + "&mm="
				+ password;
		String resultString = HttpRequest.sendPost(url, param);
		if (resultString.contains("URP �ۺϽ���ϵͳ - ��¼")) {
			return false;
		}else {
			return true;
		}
	}
	
	/**
	 * ������ģ��
	 * ��ȡѧ����Ϣ,����֮ǰ��Ҫ����stuNum
	 * @return
	 * null:ѧ����Ч
	 * @throws Exception 
	 * */
	
	public JwxtUtil.StudentInfo GetstudentInfo() throws Exception{
		if (getStuStatus() == false) {
			return null;
		}else {
			StudentInfo studentInfo = new StudentInfo();
			studentInfo.name = getName();
			studentInfo.sex = getSex();
			studentInfo.profession = getProfession();
			studentInfo.nativePlace = getProvince();
			studentInfo.className = getClassName();
			studentInfo.idCardNum = getIdCard();
			List<CourseModel>list = getCourseList();
			Map<String, String>map = new HashMap<>();
			for (int i = 0; i < list.size(); i++) {
				CourseModel courseModel = list.get(i);
				map.put(courseModel.getCourse(), courseModel.getGrade()+"");
			}
			studentInfo.testScores = map;
			return studentInfo;
		}
		
	}
	
	/**
	 * �õ��û���������Ϣ
	 * @throws Exception 
	 * */
	private String getStudentAllInfo() throws Exception{
		if (studentAllInfo == null) {
			String url=null;
			url = "http://" + getConfigInfo("host")
				+ "/setReportParams";
			String param=null;
			param = "LS_XH=" + getStuNum() + "&resultPage=http://" + getConfigInfo("host") + "/reportFiles/cj/cj_zwcjd.jsp?";
			//
			HttpRequest.setCookie(getCookie());
			this.studentAllInfo = HttpRequest.sendGet(url, param);
		}
		//System.out.println(this.studentAllInfo);
		//this.studentAllInfo = new String(this.studentAllInfo.getBytes("utf-8"), "gbk"); 
		//System.out.println(this.studentAllInfo);
		return this.studentAllInfo;
	}
	
	// �ж��û��Ƿ���Ч
	public boolean getStuStatus() throws Exception {
		if (getStudentAllInfo().contains(stuNum)) {
			return true;
		}
		return false;
	}

	// �õ����֤����
	private String getIdCard() throws Exception {
		String result = getStudentAllInfo();
		String id = "";
		Pattern pattern = Pattern.compile("[0-9]{17}[0-9xX]");
		Matcher matcher = pattern.matcher(result);
		if (matcher.find()) {
			id = matcher.group();
		}
		return id;
	}

	// �õ�����
	private String getName() throws Exception {
		String result = getStudentAllInfo();
		Pattern pattern1 = Pattern
				.compile("class=\"report1_2_1\">[^x00-xff]{2,16}</td>");
		Matcher matcher1 = pattern1.matcher(result);
		String resultString = "";
		while (matcher1.find()) {
			String tempString = matcher1.group();

			if (!tempString.contains("����")) {
				resultString = tempString;
				resultString = resultString.replaceFirst(
						"class=\"report1_2_1\">", "");
				resultString = resultString.replaceAll("</td>", "");
				break;
			}
		}
		return resultString;
	}

	// �õ��Ա�
	private String getSex() throws Exception {
		String result = getStudentAllInfo();
		if (result
				.contains("<td class=\"report1_2_9\">�Ա�</td>		<td class=\"report1_2_1\">��</td>")) {
			return "��";
		}
		if (result
				.contains("<td class=\"report1_2_9\">�Ա�</td>		<td class=\"report1_2_1\">Ů</td>")) {
			return "Ů";
		}
		return "�޷�����Ա�";
	}



	// �õ�����
	private String getNation() throws Exception {
		Pattern pattern1 = Pattern
				.compile("<td class=\"report1_2_1\">����</td>		<td colSpan=2 class=\"report1_2_1\">[^x00-xff]{2,16}");
		Matcher matcher1 = pattern1.matcher(getStudentAllInfo());
		String resultString = "";
		while (matcher1.find()) {
			resultString = matcher1.group();
		}
		return resultString
				.replaceAll(
						"<td class=\"report1_2_1\">����</td>		<td colSpan=2 class=\"report1_2_1\">",
						"");
	}

	// �õ�����
	private String getProvince() throws Exception {
		Pattern pattern1 = Pattern
				.compile("<td class=\"report1_2_1\">����</td>		<td colSpan=3 class=\"report1_2_1\">[^x00-xff]{2,16}");
		Matcher matcher1 = pattern1.matcher(getStudentAllInfo());
		String resultString = "";
		while (matcher1.find()) {
			resultString = matcher1.group();
		}

		return resultString
				.replaceAll(
						"<td class=\"report1_2_1\">����</td>		<td colSpan=3 class=\"report1_2_1\">",
						"");
	}

	// �õ�������ò
	private String getChinaRank() throws Exception {
		Pattern pattern1 = Pattern
				.compile("<td colSpan=2 class=\"report1_2_1\">������ò</td>		<td colSpan=3 class=\"report1_2_1\">[^x00-xff]{1,16}");
		Matcher matcher1 = pattern1.matcher(getStudentAllInfo());
		String resultString = "";
		while (matcher1.find()) {
			resultString = matcher1.group();
		}
		return resultString
				.replace(
						"<td colSpan=2 class=\"report1_2_1\">������ò</td>		<td colSpan=3 class=\"report1_2_1\">",
						"");
	}

	// �õ��༶
	private String getClassName() throws Exception {

		Pattern pattern1 = Pattern
				.compile("<td class=\"report1_2_1\">�༶</td>		<td colSpan=4 class=\"report1_2_1\">[^x00-xff]{1,16}[0-9]{0,3}[^x00-xff]{0,16}[0-9]{0,2}[^x00-xff]{1,16}[0-9]{0,3}[^x00-xff]{0,16}");
		Matcher matcher1 = pattern1.matcher(getStudentAllInfo());
		String resultString = "";
		while (matcher1.find()) {
			resultString = matcher1.group();
			break;
		}
		return resultString
				.replace(
						"<td class=\"report1_2_1\">�༶</td>		<td colSpan=4 class=\"report1_2_1\">",
						"");
	}

	// �õ�רҵ
	private String getProfession() throws Exception {
		Pattern pattern1 = Pattern
				.compile("<td class=\"report1_2_1\">רҵ</td>		<td colSpan=6 class=\"report1_2_1\">[^x00-xff]{1,16}");
		Matcher matcher1 = pattern1.matcher(getStudentAllInfo());
		String resultString = "";
		while (matcher1.find()) {
			resultString = matcher1.group();
			break;
		}
		return resultString
				.replace(
						"<td class=\"report1_2_1\">רҵ</td>		<td colSpan=6 class=\"report1_2_1\">",
						"");
	}

	// �õ����ſγ̵���ϸ��Ϣ
	private List<CourseModel> getCourseList() throws Exception {
		List<CourseModel> list = new ArrayList<>();
		Pattern pattern1 = Pattern
				.compile("<td colSpan=4 class=\"report1_2_1\">[^FB00��FFFDh]{0,36}</td>		<td class=\"report1_8_5\">[0-9]{1,2}</td>		<td class=\"report1_2_1\">[0-9]{1,3}</td>		<td class=\"report1_2_1\">[^FB00��FFFDh]{0,36}</td>		<td class=\"report1_2_1\">[^FB00��FFFDh]{0,36}</td>		<td colSpan=2 class=\"report1_2_1\">[0-9/]{0,9}</td>");
		Matcher matcher1 = pattern1.matcher(getStudentAllInfo());
		String resultString = "";
		while (matcher1.find()) {
			resultString = matcher1.group();
			list.add(getCourseModel(resultString));
		}
		return list;
		// return
		// resultString.replace("<td class=\"report1_2_1\">רҵ</td>		<td colSpan=6 class=\"report1_2_1\">",
		// "");
	}

	public CourseModel getCourseModel(String CourseInfo) {
		String[] results = new String[6];
		int i = 0;
		CourseModel courseModel = new CourseModel();
		Pattern pattern = Pattern
				.compile(">[^FB00��FFFDh]{0,16}[0-9/]{0,9}</td>");
		Matcher matcher = pattern.matcher(CourseInfo);
		String resultString = "";
		while (matcher.find()) {
			resultString = matcher.group();
			resultString = resultString.replace("</td>", "");
			resultString = resultString.replace(">", "");
			results[i++] = resultString;
		}
		courseModel.setCourse(results[0]);
		courseModel.setPoint(Integer.parseInt(results[1]));
		try {
			courseModel.setGrade(Integer.parseInt(results[2]));
		} catch (Exception e) {
			// TODO: handle exception
			courseModel.setGrade(Integer.parseInt("0"));
		}

		courseModel.setDate(results[5]);
		return courseModel;
	}
	
	/**
	 * ������ģ��
	 * ������������
	 * @param
	 * name ����
	 * @throws Exception 
	 * */
	
	public List<StudentInfo>getStudentInfoListByName(String name) throws Exception{
		HttpRequest.setCookie(getCookie());
		List<String>stuNumList = new ArrayList<>();
		String url = "http://"+ getConfigInfo("host") +"/xsmdAction.do?oper=xsmd";
		String param = "xh=&xm="+URLEncoder.encode(name, "gbk")+"&zymc=&zyfxmc=&ts=500&submitButton=%B2%E9%D1%AF";
		String allStuNumInfo = HttpRequest.sendPost(url, param);
		//System.out.println(allStuNumInfo);
		Pattern pattern = Pattern
				.compile("<input  type=\"hidden\" name=\"cxkbdm\" value=\"[0-9]{10}\" />");
		Matcher matcher1 = pattern.matcher(allStuNumInfo);
		String resultString = "";
		while (matcher1.find()) {
			resultString = matcher1.group();
			resultString = resultString.replaceAll("<input  type=\"hidden\" name=\"cxkbdm\" value=\"", "");
			resultString = resultString.replaceAll("\" />", "");
			stuNumList.add(resultString);
		}
		
		List<StudentInfo>list = new ArrayList<>();
		for (int i = 0; i < stuNumList.size(); i++) {
			//System.out.println(stuNumList.get(i));
			setStuNum(stuNumList.get(i));
			list.add(GetstudentInfo());
		}
		return list;
	}
}

