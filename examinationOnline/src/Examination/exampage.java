/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Examination;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 *
 * @author KHUSHI VERMA
 */
public class exampage extends javax.swing.JFrame {

    /**
     * Creates new form exampage
     */
      Connection con;
    PreparedStatement pst;
    ResultSet rs , rs1;
     String stuname , rolln,ques , op1 , op2 , op3 , op4 , ans  , qid = "1" ,stdans , marks1;
     int  marksvar =0 , min =0, sec=0;
     
     public void answercheck(){
         if(opt1btn.isSelected()){
             stdans = opt1btn.getText();
         }
        else if(opt2btn.isSelected()){
             stdans = opt2btn.getText();
         }
        else if(opt3btn.isSelected()){
             stdans = opt3btn.getText();
         }
        else {
             stdans = opt4btn.getText();
         }
     
         if(stdans.equals(ans)){
             marksvar = marksvar + 5;
       
         }
         
         opt1btn.setSelected(false);
         opt2btn.setSelected(false);
         opt3btn.setSelected(false);
         opt4btn.setSelected(false);
         
         int qid2 = Integer.parseInt(qid);
         qid2 = qid2 +1;
         qid = String.valueOf(qid2);
         
         if(qid.equals("10")){
             nextbtn.setVisible(false);
         }
     }
     public void submit(){
         answercheck();
    try {
        String query = "UPDATE registerstud SET marks = ? where stuID = ?";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setInt(1, marksvar);
        stmt.setInt(2, myrollnum);
        stmt.executeUpdate();
    } catch (SQLException ex) {
        Logger.getLogger(exampage.class.getName()).log(Level.SEVERE, null, ex);
    }     
     }
     public void questions(){
          try {
             pst = con.prepareStatement("select * from examques where quesID ='"+qid+"'");
              rs1 = pst.executeQuery();
              while(rs1.next()){
              qidlbl.setText(String.valueOf(rs1.getInt(1)));
              queslbl.setText(rs1.getString(2));
              opt1btn.setText(rs1.getString(3));
              opt2btn.setText(rs1.getString(4));
              opt3btn.setText(rs1.getString(5));
              opt4btn.setText(rs1.getString(6));
              ans = rs1.getString(7);
          }
          } catch (SQLException ex) {
             Logger.getLogger(exampage.class.getName()).log(Level.SEVERE, null, ex);
          }
     }

     
    public exampage() {
        initComponents();
        Connect();
        scaleimg();
               
    }
    int myrollnum;
    Timer time;
      public exampage(int rollnum) {
        initComponents();
        Connect();
        scaleimg();
        
        myrollnum = rollnum;
        rollnolbl.setText("" +myrollnum);
    
      SimpleDateFormat dformat = new SimpleDateFormat("dd-MM-yyyy");
      Date date = new Date(System.currentTimeMillis());
     datelbl.setText(dformat.format(date));
      
          try {
              pst = con.prepareStatement("select * from registerstud where stuID = ?");
              pst.setInt(1, myrollnum);
              rs = pst.executeQuery();
              if(rs.next()){
                  namelbl.setText(rs.getString(2));
              }
              rs1 = pst.executeQuery("select * from examques where quesID = '"+qid+"'");
              while(rs1.next()){
              qidlbl.setText(String.valueOf(rs1.getInt(1)));
              queslbl.setText(rs1.getString(2));
              opt1btn.setText(rs1.getString(3));
              opt2btn.setText(rs1.getString(4));
              opt3btn.setText(rs1.getString(5));
              opt4btn.setText(rs1.getString(6));
              ans = rs1.getString(7);
              totalq.setText("10");
              timelbl.setText("10 Min");
          }
          } catch (SQLException ex) {
             Logger.getLogger(exampage.class.getName()).log(Level.SEVERE, null, ex);
          }
          setLocationRelativeTo(this);
            time = new Timer(1000 , new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent ex){
                    seclbl.setText(String.valueOf(sec));
                    minlbl.setText(String.valueOf(min));
                    
                    if(sec == 60){
                        sec =0; 
                        min++;
                        if(min == 10)
                        {
                            time.stop();
                            answercheck();
                            submit();
                        }
                    }
                    sec++;
                }
                    }
            
            );
            time.start();
              
            
      }  
       public void scaleimg(){
        ImageIcon icon = new ImageIcon("C:\\Users\\KHUSHI VERMA\\Documents\\NetBeansProjects\\examinationOnline\\test\\academic.png");
         ImageIcon icon2 = new ImageIcon("C:\\Users\\KHUSHI VERMA\\Documents\\NetBeansProjects\\examinationOnline\\test\\exam_icon_182959.png");
         ImageIcon icon3 = new ImageIcon("C:\\Users\\KHUSHI VERMA\\Documents\\NetBeansProjects\\examinationOnline\\test\\test_exam_icon_182954.png");
        Image img = icon.getImage();
          Image img2 = icon2.getImage();
          Image img3 = icon3.getImage();
        
        Image imgscale = img.getScaledInstance(iconlbl.getWidth(),iconlbl.getHeight(), Image.SCALE_SMOOTH);
                
        Image imgscale2 = img2.getScaledInstance(iconlbl2.getWidth(),iconlbl2.getHeight(), Image.SCALE_SMOOTH);
        Image imgscale3= img3.getScaledInstance(iconlbl3.getWidth(),iconlbl3.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaleicon = new ImageIcon(imgscale);
         ImageIcon scaleicon2 = new ImageIcon(imgscale2);
         ImageIcon scaleicon3 = new ImageIcon(imgscale3);
        iconlbl.setIcon(scaleicon);
         iconlbl2.setIcon(scaleicon2);
         iconlbl3.setIcon(scaleicon3);
    }
  
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    public void Connect(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/ examination","root" , "");
        } catch (ClassNotFoundException | SQLException ex) {
            java.util.logging.Logger.getLogger(registerstud.class.getName()).log(Level.SEVERE, null, ex);
        }
  }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        timelbl = new javax.swing.JLabel();
        datelbl = new javax.swing.JLabel();
        minlbl = new javax.swing.JLabel();
        seclbl = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        iconlbl2 = new javax.swing.JLabel();
        iconlbl3 = new javax.swing.JLabel();
        quespanel = new javax.swing.JPanel();
        nextbtn = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        queslbl = new javax.swing.JLabel();
        opt1btn = new javax.swing.JRadioButton();
        opt2btn = new javax.swing.JRadioButton();
        opt3btn = new javax.swing.JRadioButton();
        opt4btn = new javax.swing.JRadioButton();
        qidlbl = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        rollno = new javax.swing.JPanel();
        namelbl = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        rollnolbl = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        iconlbl = new javax.swing.JLabel();
        totalq = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 153));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 48)); // NOI18N
        jLabel1.setText("ONLINE EXAM");

        jLabel7.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        jLabel7.setText("Time : ");

        jLabel8.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        jLabel8.setText("Timer:");

        jLabel9.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        jLabel9.setText("Date :");

        timelbl.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        timelbl.setText("jLabel10");

        datelbl.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        datelbl.setText("jLabel11");

        minlbl.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        minlbl.setText("jLabel12");

        seclbl.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        seclbl.setText("jLabel3");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel6.setText(":");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(iconlbl2, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(78, 78, 78)
                .addComponent(jLabel1)
                .addGap(72, 72, 72)
                .addComponent(iconlbl3, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(123, 123, 123)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel9))
                        .addGap(32, 32, 32)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(timelbl)
                            .addComponent(datelbl))
                        .addGap(167, 167, 167))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(minlbl)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(seclbl)
                        .addGap(97, 97, 97))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(timelbl)
                    .addComponent(jLabel7))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(44, 44, 44)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel9)
                                    .addComponent(datelbl)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addComponent(jLabel1)))
                        .addGap(23, 23, 23)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(minlbl)
                            .addComponent(seclbl)
                            .addComponent(jLabel6)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(iconlbl3, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(iconlbl2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(66, Short.MAX_VALUE))
        );

        quespanel.setBackground(new java.awt.Color(204, 255, 204));

        nextbtn.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        nextbtn.setText("NEXT");
        nextbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextbtnActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        jButton2.setText("SUBMIT");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        queslbl.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        queslbl.setText("QUESTIONS");

        opt1btn.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        opt1btn.setText("jRadioButton1");
        opt1btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opt1btnActionPerformed(evt);
            }
        });

        opt2btn.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        opt2btn.setText("jRadioButton2");
        opt2btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opt2btnActionPerformed(evt);
            }
        });

        opt3btn.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        opt3btn.setText("jRadioButton3");
        opt3btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opt3btnActionPerformed(evt);
            }
        });

        opt4btn.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        opt4btn.setText("jRadioButton4");
        opt4btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opt4btnActionPerformed(evt);
            }
        });

        qidlbl.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        qidlbl.setText("jLabel3");

        jLabel3.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        jLabel3.setText("QUESTION NO:-");

        javax.swing.GroupLayout quespanelLayout = new javax.swing.GroupLayout(quespanel);
        quespanel.setLayout(quespanelLayout);
        quespanelLayout.setHorizontalGroup(
            quespanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(quespanelLayout.createSequentialGroup()
                .addGap(481, 481, 481)
                .addComponent(nextbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addGap(216, 216, 216))
            .addGroup(quespanelLayout.createSequentialGroup()
                .addGroup(quespanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(quespanelLayout.createSequentialGroup()
                        .addGap(122, 122, 122)
                        .addGroup(quespanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(opt4btn)
                            .addComponent(opt3btn)
                            .addComponent(opt2btn)
                            .addGroup(quespanelLayout.createSequentialGroup()
                                .addGap(454, 454, 454)
                                .addComponent(jLabel3)
                                .addGap(36, 36, 36)
                                .addComponent(qidlbl))
                            .addComponent(opt1btn)))
                    .addGroup(quespanelLayout.createSequentialGroup()
                        .addGap(133, 133, 133)
                        .addComponent(queslbl, javax.swing.GroupLayout.PREFERRED_SIZE, 1038, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        quespanelLayout.setVerticalGroup(
            quespanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(quespanelLayout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addGroup(quespanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(qidlbl))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(queslbl)
                .addGap(58, 58, 58)
                .addComponent(opt1btn)
                .addGap(78, 78, 78)
                .addComponent(opt2btn)
                .addGap(66, 66, 66)
                .addComponent(opt3btn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(opt4btn)
                .addGap(68, 68, 68)
                .addGroup(quespanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nextbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27))
        );

        rollno.setBackground(new java.awt.Color(255, 153, 102));

        namelbl.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        namelbl.setText("jLabel2");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel2.setText("Roll No:-");

        rollnolbl.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        rollnolbl.setText("jLabel3");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setText("Total Questions :-");

        jLabel5.setBackground(new java.awt.Color(255, 153, 153));
        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel5.setText("INSTRUCTIONS");

        totalq.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        totalq.setText("jLabel10");

        javax.swing.GroupLayout rollnoLayout = new javax.swing.GroupLayout(rollno);
        rollno.setLayout(rollnoLayout);
        rollnoLayout.setHorizontalGroup(
            rollnoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rollnoLayout.createSequentialGroup()
                .addGroup(rollnoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(rollnoLayout.createSequentialGroup()
                        .addGap(130, 130, 130)
                        .addGroup(rollnoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(iconlbl, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rollnoLayout.createSequentialGroup()
                                .addComponent(namelbl)
                                .addGap(25, 25, 25))))
                    .addGroup(rollnoLayout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addGroup(rollnoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel2))
                        .addGap(30, 30, 30)
                        .addGroup(rollnoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rollnolbl)
                            .addComponent(totalq)))
                    .addGroup(rollnoLayout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addComponent(jLabel5)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        rollnoLayout.setVerticalGroup(
            rollnoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rollnoLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(iconlbl, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(namelbl)
                .addGap(40, 40, 40)
                .addGroup(rollnoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rollnolbl)
                    .addComponent(jLabel2))
                .addGap(35, 35, 35)
                .addGroup(rollnoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(totalq))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 88, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addContainerGap())
        );

        jTextArea1.setBackground(new java.awt.Color(255, 204, 153));
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Palatino Linotype", 1, 18)); // NOI18N
        jTextArea1.setRows(5);
        jTextArea1.setText("1.  The examination will comprise \n     of Objective type Multiple Choice\n     Questions (MCQs) \n2.  All questions are compulsory \n     and each carries One mark.\n3.  The total number of questions,\n    duration of examination, will be                                                          \n     different based onthe course, \n    the detail is available on your \n     screen.\n4.  The students just need to click\n     on the Right Choice / Correct                                                     \n     option from the multiple choices/\n     options given with each\n     question. For Multiple Choice \n     Questions,each question has four \n     options,and the candidate has to\n     click the appropriate option.\n5.   There will be NO NEGATIVE\n     MARKING for the wrong answers.");
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(rollno, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 402, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(quespanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(3, 3, 3))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(quespanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(30, 30, 30))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(rollno, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 504, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void nextbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextbtnActionPerformed
        // TODO add your handling code here:
        answercheck();
        questions();
    }//GEN-LAST:event_nextbtnActionPerformed

    private void opt1btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opt1btnActionPerformed
        // TODO add your handling code here:
        if(opt1btn.isSelected()){
            opt2btn.setSelected(false);
             opt3btn.setSelected(false);
              opt4btn.setSelected(false);
            
        }
    }//GEN-LAST:event_opt1btnActionPerformed

    private void opt2btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opt2btnActionPerformed
        // TODO add your handling code here:
         if(opt2btn.isSelected()){
            opt1btn.setSelected(false);
             opt3btn.setSelected(false);
              opt4btn.setSelected(false);
            
        }
    }//GEN-LAST:event_opt2btnActionPerformed

    private void opt3btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opt3btnActionPerformed
        // TODO add your handling code here:
         if(opt3btn.isSelected()){
            opt1btn.setSelected(false);
             opt2btn.setSelected(false);
              opt4btn.setSelected(false);
            
        }
    }//GEN-LAST:event_opt3btnActionPerformed

    private void opt4btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opt4btnActionPerformed
        // TODO add your handling code here:
        if(opt4btn.isSelected()){
            opt1btn.setSelected(false);
             opt2btn.setSelected(false);
              opt3btn.setSelected(false);
            
        }
    }//GEN-LAST:event_opt4btnActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
         int result = JOptionPane.showConfirmDialog(null,"Do you really want to submit?" , "Confirm", JOptionPane.YES_NO_OPTION);
    if(result == JOptionPane.YES_OPTION)
    {
         answercheck();
         submit();
         setVisible(false);
        new congrats(myrollnum).setVisible(true);
    }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        // TODO add your handling code here:
       
    }//GEN-LAST:event_jButton2MouseClicked
     
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(exampage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(exampage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(exampage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(exampage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new exampage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JLabel datelbl;
    private javax.swing.JLabel iconlbl;
    private javax.swing.JLabel iconlbl2;
    private javax.swing.JLabel iconlbl3;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel minlbl;
    private javax.swing.JLabel namelbl;
    private javax.swing.JButton nextbtn;
    private javax.swing.JRadioButton opt1btn;
    private javax.swing.JRadioButton opt2btn;
    private javax.swing.JRadioButton opt3btn;
    private javax.swing.JRadioButton opt4btn;
    private javax.swing.JLabel qidlbl;
    private javax.swing.JLabel queslbl;
    private javax.swing.JPanel quespanel;
    private javax.swing.JPanel rollno;
    private javax.swing.JLabel rollnolbl;
    private javax.swing.JLabel seclbl;
    private javax.swing.JLabel timelbl;
    private javax.swing.JLabel totalq;
    // End of variables declaration//GEN-END:variables
}
