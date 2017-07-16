package me.hupeng.homeworkweb.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.hupeng.homeworkweb.model.MessageModel;
import me.hupeng.homeworkweb.service.HomeworkService;

import org.apache.struts2.ServletActionContext;
import org.hibernate.type.TrueFalseType;

public class FileUploadAction extends ActionSupport {
//	private String allowType="text/plain,text/xml,application/zip,application/octet-stream,application/msword,application/vnd.ms-powerpoint,application/vnd.ms-excel,image/jpeg,image/png,image/gif,image/bmp,application/x-gzip";
    // ��װ�ϴ��ļ��������
    private File file;
    // ��װ�ϴ��ļ����͵�����
    private String fileContentType;
    // ��װ�ϴ��ļ���������
    private String fileFileName;
    // ��������ע�������
    private String savePath;
    private String taskId;
    private MessageModel messageModel;
    private HomeworkService homeworkService;
    private String filePath;
    private String fullPath;
    public File getFile() {
		return file;
	}
    
	public void setFile(File file) {
		this.file = file;
	}
	
	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public MessageModel getMessageModel() {
		return messageModel;
	}

	public void setMessageModel(MessageModel messageModel) {
		this.messageModel = messageModel;
	}

	public HomeworkService getHomeworkService() {
		return homeworkService;
	}

	public void setHomeworkService(HomeworkService homeworkService) {
		this.homeworkService = homeworkService;
	}

	@Override
    public String execute() throws Exception {
//		taskId = "4";
		//�û������֤
		Integer taskIdInteger;
		messageModel = new MessageModel();
		try {
			taskIdInteger = Integer.parseInt(taskId);
			if (!(taskIdInteger+"").equals(taskId)) {
				throw new Exception();
			}
		} catch (Exception e) {
			// TODO: handle exception
			messageModel.setText("ԭ�򣺲����Ƿ�������IP��ַ" + ServletActionContext.getRequest().getRemoteAddr()+"�ѱ�ϵͳ��¼");
			messageModel.setTitle("�ϴ�������ϴ�ʧ��");
			messageModel.setUrl("taskInfo.action");
			return "message";
		}
		
		
		if (fileContentType == null ) {
			messageModel.setTitle("�ϴ�������ϴ�ʧ��");
			messageModel.setText("ԭ������ѡ���ļ����ϴ�");
			messageModel.setUrl("taskInfo.action?taskId=" + taskId);
			return "message";
		}
//		if (!allowType.contains(fileContentType)) {
//			messageModel.setTitle("�ϴ�������ϴ�ʧ��");
//			messageModel.setText("ԭ���ļ����Ͳ�������ǰ������ļ����ͣ�" + allowType + "������Ҫ֧�ָ����ļ����ͣ�����ϵ����Ա��");
//			messageModel.setUrl("taskInfo.action?taskId=" + taskId);
//			return "message";
//		}
		
		SecurityAction securityAction = new SecurityAction();
		if (!securityAction.checkUser()) {
			return LOGIN;
		}
		
        FileOutputStream fos = null;
        FileInputStream fis = null;
        try {
            // �����ļ������
            filePath = getSavePath();
            savePath += "/" + taskId;
            File tempFile = null;
            tempFile = new File(savePath);
            if (!tempFile.exists()) {
                tempFile.mkdirs();
    		}
            fullPath = savePath + "/" + securityAction.getStuNum() + "_" + ServletActionContext.getRequest().getSession().getAttribute("name") + "." +getFileFileName().split("\\.")[getFileFileName().split("\\.").length-1];
            fos = new FileOutputStream(fullPath);
            // �����ļ��ϴ��� 
            fis = new FileInputStream(getFile());
            byte[] buffer = new byte[10240];
            int len = 0;
            while ((len = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
        } catch (Exception e) {
            System.out.println("�ļ��ϴ�ʧ��");
            messageModel.setTitle("�ϴ�������ϴ�ʧ��");
			messageModel.setText("ԭ��" + e);
			messageModel.setUrl("taskInfo.action?taskId=" + taskId);
            e.printStackTrace();
			return "message";
        } finally {
            close(fos, fis);
        }
        //����������ݿ�д�����*********************************x
//        homeworkService.add(securityAction.getUserId(),taskIdInteger, fullPath.replace(filePath.replace("uploadFile", ""), ""));
        homeworkService.add(securityAction.getUserId(),taskIdInteger, fullPath);
        //************************************************
        messageModel.setTitle("�ϴ�������ϴ��ɹ�");
		messageModel.setText("�ϴ��ߣ�" + securityAction.getUsername() + "\n" + "ʱ�����" + System.currentTimeMillis()/1000 + "\n" + "�ļ�����" + fileFileName);
		messageModel.setUrl("taskInfo.action?taskId=" + taskId);
        return "message";
    }

    /**
     * �����ϴ��ļ��ı���λ��
     * 
     * @return
     */
    public String getSavePath() throws Exception{

    	if (savePath == null) {
    		 ServletActionContext.getServletContext().getRealPath(savePath);
		}
    	savePath = "/home/HomeworkWeb" +savePath;
        return savePath;
    }

    
    private void close(FileOutputStream fos, FileInputStream fis) {
        if (fis != null) {
            try {
                fis.close();
            } catch (IOException e) {
                System.out.println("FileInputStream�ر�ʧ��");
                e.printStackTrace();
            }
        }
        if (fos != null) {
            try {
                fos.close();
            } catch (IOException e) {
                System.out.println("FileOutputStream�ر�ʧ��");
                e.printStackTrace();
            }
        }
    }
}