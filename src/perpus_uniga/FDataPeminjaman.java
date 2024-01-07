/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package perpus_uniga;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.text.ParseException;

/**
 *
 * @author ASUS VIVOBOOK
 */
public class FDataPeminjaman extends javax.swing.JFrame {

    /**
     * Creates new form FDataPeminjaman
     */
    public FDataPeminjaman() {
        initComponents();
        load_data(); //Memanggil Menampilkan Data
        IDOtomatis();
        LoadIDAnggota();
        LoadKodeBuku();
    }
    
    private void load_data()
    {
        Connection kon=koneksi.koneksiDb();
        Object header[]={"ID PEMINJAMAN","ID ANGGOTA","TANGGAL PINJAM","TANGGAL KEMBALI","KODE BUKU","STATUS"};
        DefaultTableModel data=new DefaultTableModel(null,header);
        TabelPeminjaman.setModel (data);
        String sql_data = "SELECT * FROM t_peminjaman";
        try
        {
            Statement st=kon.createStatement();
            ResultSet rs=st.executeQuery(sql_data);
            while(rs.next())
            {
                String d1=rs.getString(1);
                String d2=rs.getString(2);
                String d3=rs.getString(3);
                String d4=rs.getString(4);
                String d5=rs.getString(5);
                String d6=rs.getString(6);
                
                
                String d[]={d1,d2,d3,d4,d5,d6};
                data.addRow(d);
            
            }
        }
        catch(Exception e)
                {
                    JOptionPane.showMessageDialog(null, e);
                }
    }
    
//ID Peminjaman Otomatis
    private void IDOtomatis()
    {
        try
        {
            Connection kon=koneksi.koneksiDb();
            Statement st=kon.createStatement();
            String sql_idp="SELECT * FROM t_peminjaman order by id_peminjaman desc";
            ResultSet rs=st.executeQuery(sql_idp);
            if(rs.next())
            {
                String id_peminjaman=rs.getString("id_peminjaman").substring(1);
                String AN=""+(Integer.parseInt(id_peminjaman)+1);
                String No1="";
                if(AN.length()==1) //Jika id_anggota A00001
                {
                    No1="0000";
                }
                else if(AN.length()==2) //Jika id_anggota A00010
                {
                    No1="000";
                }
                else if(AN.length()==3) //Jika id_anggota A00100
                {
                    No1="00";
                }
                IDPEMINJAMAN.setText("M"+No1+AN);
            }
            else
            {
                IDPEMINJAMAN.setText("M00001");
            }
        }
        catch(Exception e)
                {
                    JOptionPane.showMessageDialog(null,e);
                }
    }
    
    //LOAD COMBOBOX ID ANGGOTA
    private void LoadIDAnggota()
    {
        try
        {
            Connection kon=koneksi.koneksiDb();
            Statement st=kon.createStatement();
            String sql_load_combo="SELECT * FROM t_anggota";
            ResultSet rs=st.executeQuery(sql_load_combo);
            while(rs.next())
            {
                IDANGGOTA.addItem(rs.getString("id_anggota"));
            }
            rs.close();
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    //LOAD COMBOBOX KODE BUKU
    private void LoadKodeBuku()
    {
        try
        {
            Connection kon=koneksi.koneksiDb();
            Statement st=kon.createStatement();
            String sql_load_combo="SELECT * FROM t_buku";
            ResultSet rs=st.executeQuery(sql_load_combo);
            while(rs.next())
            {
                KODEBUKU.addItem(rs.getString("kode_buku"));
            }
            rs.close();
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    //INPUT DATA-----------------------------------------------------------
    private void input_data()
    {
        try
        {
        Connection kon=koneksi.koneksiDb();
        Statement st=kon.createStatement(); 
        // Validasi - pastikan semua kolom terisi
        if (IDPEMINJAMAN.getText().isEmpty() || IDANGGOTA.getSelectedItem() == null 
                || TANGGALPINJAM.getDate() == null || TANGGALKEMBALI.getDate() == null || KODEBUKU.getSelectedItem() == null 
                || STATUSPINJAM.getSelectedItem() == null ) {
            JOptionPane.showMessageDialog(null, "Harap isi semua kolom sebelum menambahkan data.");
            return; // Jika ada yang kosong, hentikan eksekusi
        }
        
        // Ambil nilai tanggal dari JDateChooser
        Date selectedDateP = TANGGALPINJAM.getDate();
        Date selectedDateK = TANGGALKEMBALI.getDate();
        
        String selectedAnggota = IDANGGOTA.getSelectedItem().toString();
        String selectedBuku = KODEBUKU.getSelectedItem().toString();
        String selectedStatusPeminjaman = STATUSPINJAM.getSelectedItem().toString();
        

        // Gunakan SimpleDateFormat untuk memformat tanggal
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDateP = dateFormat.format(selectedDateP);
        String formattedDateK = dateFormat.format(selectedDateK);

        String sql="INSERT INTO t_peminjaman values('"+IDPEMINJAMAN.getText()
                +"','"+selectedAnggota
                +"','" + formattedDateP
                +"','" + formattedDateK
                +"','"+selectedBuku
                +"','"+selectedStatusPeminjaman
                +"')";
        st.execute(sql);
        JOptionPane.showMessageDialog(null, "Data Peminjaman Berhasil Dimasukkan");
        }
        catch(Exception e)
            {
               JOptionPane.showMessageDialog(null, e);
            }
    }
    //Reset Data Setelah Input/Edit/Delete 
   public void clear() {
    // Reset combobox for IDANGGOTA
    IDANGGOTA.setSelectedItem("");

    // Reset JCalendar for TANGGALPINJAM and TANGGALKEMBALI
    TANGGALPINJAM.setDate(null);
    TANGGALKEMBALI.setDate(null);

    // Reset combobox for KODEBUKU
    KODEBUKU.setSelectedItem("");

    // Reset combobox for STATUSPINJAM
    STATUSPINJAM.setSelectedItem("FAR");
}
  
   
    //Edit Data
    public void update()
    {
        try
        {
            Connection kon=koneksi.koneksiDb();
            Statement st=kon.createStatement();
            
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDatePinjam = dateFormat.format(TANGGALPINJAM.getDate());
            String formattedDateKembali = dateFormat.format(TANGGALKEMBALI.getDate());
            
            String sql_update="UPDATE t_peminjaman SET id_anggota='"+IDANGGOTA.getSelectedItem()
                    + "',tgl_pinjam='" + formattedDatePinjam
                    + "',tgl_harap_kembali='" + formattedDateKembali
                    +"',kode_buku='"+KODEBUKU.getSelectedItem()
                    +"',status_peminjaman='"+STATUSPINJAM.getSelectedItem()
                    +"'WHERE id_peminjaman='"+IDPEMINJAMAN.getText()+"'";
            st.execute(sql_update);
            JOptionPane.showMessageDialog(null, "Data Berhasil Di Update");
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
    }
   
   //Delete Data Petugas
    private void delete()
    {
        try
        {
          Connection kon=koneksi.koneksiDb();
          Statement st=kon.createStatement();  
          String sql_delete="DELETE from t_peminjaman WHERE "
                  +"id_peminjaman='"+IDPEMINJAMAN.getText()+"'";
          st.executeUpdate(sql_delete);
          JOptionPane.showMessageDialog(null,"Data Berhasil Dihapus");
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        IDPEMINJAMAN = new javax.swing.JTextField();
        TANGGALPINJAM = new com.toedter.calendar.JDateChooser();
        TANGGALKEMBALI = new com.toedter.calendar.JDateChooser();
        STATUSPINJAM = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        TabelPeminjaman = new javax.swing.JTable();
        BKELUAR = new javax.swing.JButton();
        BINPUT = new javax.swing.JButton();
        BEDIT = new javax.swing.JButton();
        BDELETE = new javax.swing.JButton();
        IDANGGOTA = new javax.swing.JComboBox<>();
        KODEBUKU = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Data Peminjaman");

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));
        jPanel1.setToolTipText("");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel3.setText("KELOLA DATA PEMINJAMAN");

        jLabel1.setText("ID PEMINJAMAN");

        jLabel2.setText("ID ANGGOTA");

        jLabel4.setText("TANGGAL PINJAM");

        jLabel5.setText("TANGGAL KEMBALI");

        jLabel6.setText("KODE BUKU");

        jLabel7.setText("STATUS PEMINJAMAN");

        IDPEMINJAMAN.setEnabled(false);

        STATUSPINJAM.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Berlangsung", "Selesai" }));

        TabelPeminjaman.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        TabelPeminjaman.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabelPeminjamanMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TabelPeminjaman);

        BKELUAR.setText("KELUAR");
        BKELUAR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BKELUARActionPerformed(evt);
            }
        });

        BINPUT.setText("INPUT");
        BINPUT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BINPUTActionPerformed(evt);
            }
        });

        BEDIT.setText("EDIT");
        BEDIT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BEDITActionPerformed(evt);
            }
        });

        BDELETE.setText("DELETE");
        BDELETE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BDELETEActionPerformed(evt);
            }
        });

        IDANGGOTA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IDANGGOTAActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(BKELUAR)
                        .addGap(18, 18, 18)
                        .addComponent(BINPUT)
                        .addGap(18, 18, 18)
                        .addComponent(BEDIT)
                        .addGap(18, 18, 18)
                        .addComponent(BDELETE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addGap(25, 25, 25)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(TANGGALPINJAM, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(STATUSPINJAM, javax.swing.GroupLayout.Alignment.LEADING, 0, 156, Short.MAX_VALUE)
                                    .addComponent(TANGGALKEMBALI, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(IDANGGOTA, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(KODEBUKU, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(21, 21, 21))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(IDPEMINJAMAN, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 577, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(36, 36, 36))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(IDPEMINJAMAN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(IDANGGOTA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(TANGGALPINJAM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(TANGGALKEMBALI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(KODEBUKU, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(STATUSPINJAM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BKELUAR)
                    .addComponent(BINPUT)
                    .addComponent(BEDIT)
                    .addComponent(BDELETE))
                .addContainerGap(37, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BKELUARActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BKELUARActionPerformed
        // TODO add your handling code here:
        int keluar;
        keluar = JOptionPane.showOptionDialog(this,
                "Keluar dari Aplikasi?",
                "Exit",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, null,null);
        if(keluar==JOptionPane.YES_NO_OPTION)
        {
        new FUtama() .show();
        this.dispose();
        }  
    }//GEN-LAST:event_BKELUARActionPerformed

    private void IDANGGOTAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IDANGGOTAActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_IDANGGOTAActionPerformed

    private void BINPUTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BINPUTActionPerformed
        int simpan = JOptionPane.showOptionDialog(this,
                "Apakah Data Sudah Benar?",
                "Simpan Data",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,null,null,null);
        if(simpan==JOptionPane.YES_OPTION)
        {
            input_data();
            load_data();
            clear();
            IDOtomatis();
        }
    }//GEN-LAST:event_BINPUTActionPerformed

    private void BDELETEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BDELETEActionPerformed
     int delete = JOptionPane.showOptionDialog(this,
                "Apakah Data Akan Di Hapus?",
                "Hapus Data",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, null,null);
        if(delete==JOptionPane.YES_NO_OPTION)
        {
        delete();
        clear();
        load_data();
        IDOtomatis();
        //Set Enablet INPUT
        BINPUT.setEnabled(true);
        BEDIT.setEnabled(false);
        BDELETE.setEnabled(false);
        }
    }//GEN-LAST:event_BDELETEActionPerformed

    private void BEDITActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BEDITActionPerformed
        // TODO add your handling code here:
        int update = JOptionPane.showOptionDialog(this,
                "Apakah Data Akan di Update?",
                "Update Data",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,null,null,null);
        if(update==JOptionPane.YES_OPTION)
        {
        update();
        clear();
        load_data();
        IDOtomatis();
        //Set Enable INPUT
        BINPUT.setEnabled(false);
        BEDIT.setEnabled(true);
        BDELETE.setEnabled(true); 
        }
    }//GEN-LAST:event_BEDITActionPerformed

    private void TabelPeminjamanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabelPeminjamanMouseClicked
        // TODO add your handling code here:
                // TODO add your handling code here:
        int bar=TabelPeminjaman.getSelectedRow();
        String a=TabelPeminjaman.getValueAt(bar, 0).toString();
        String b=TabelPeminjaman.getValueAt(bar, 1).toString();
        String c=TabelPeminjaman.getValueAt(bar, 2).toString();
        String d=TabelPeminjaman.getValueAt(bar, 3).toString();
        String e=TabelPeminjaman.getValueAt(bar, 4).toString();
        String f=TabelPeminjaman.getValueAt(bar, 5).toString();
        
        IDPEMINJAMAN.setText(a);
        IDANGGOTA.setSelectedItem(b);
        // Set tanggal pada JCalendar
        try {
            Date tanggal = new SimpleDateFormat("yyyy-MM-dd").parse(c);
            TANGGALPINJAM.setDate(tanggal);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        try {
            Date tanggal = new SimpleDateFormat("yyyy-MM-dd").parse(d);
            TANGGALKEMBALI.setDate(tanggal);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        KODEBUKU.setSelectedItem(e);
        STATUSPINJAM.setSelectedItem(f);
        
        //Set Disable Input
        BINPUT.setEnabled(false);
        BEDIT.setEnabled(true);
        BDELETE.setEnabled(true); 
    }//GEN-LAST:event_TabelPeminjamanMouseClicked

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
            java.util.logging.Logger.getLogger(FDataPeminjaman.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FDataPeminjaman.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FDataPeminjaman.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FDataPeminjaman.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FDataPeminjaman().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BDELETE;
    private javax.swing.JButton BEDIT;
    private javax.swing.JButton BINPUT;
    private javax.swing.JButton BKELUAR;
    private javax.swing.JComboBox<String> IDANGGOTA;
    private javax.swing.JTextField IDPEMINJAMAN;
    private javax.swing.JComboBox<String> KODEBUKU;
    private javax.swing.JComboBox<String> STATUSPINJAM;
    private com.toedter.calendar.JDateChooser TANGGALKEMBALI;
    private com.toedter.calendar.JDateChooser TANGGALPINJAM;
    private javax.swing.JTable TabelPeminjaman;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
