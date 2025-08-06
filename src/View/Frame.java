package View;

import Controller.Main;
import Model.User;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.sql.Timestamp;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

public class Frame extends javax.swing.JFrame {

      // Added user session management (fixes security issues #2 and #3)
    private User currentUser = null; // Keeps track of who's logged in
    private long sessionStartTime = 0; // When did they log in?
    private static final long SESSION_TIMEOUT_MS = 30 * 60 * 1000; // 30 minutes session timeout
    
    public Frame() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Container = new javax.swing.JPanel();
        HomePnl = new javax.swing.JPanel();
        Content = new javax.swing.JPanel();
        Navigation = new javax.swing.JPanel();
        adminBtn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        managerBtn = new javax.swing.JButton();
        staffBtn = new javax.swing.JButton();
        clientBtn = new javax.swing.JButton();
        logoutBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(153, 153, 153));
        setMinimumSize(new java.awt.Dimension(800, 450));

        HomePnl.setBackground(new java.awt.Color(102, 102, 102));
        HomePnl.setPreferredSize(new java.awt.Dimension(801, 500));

        javax.swing.GroupLayout ContentLayout = new javax.swing.GroupLayout(Content);
        Content.setLayout(ContentLayout);
        ContentLayout.setHorizontalGroup(
            ContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 544, Short.MAX_VALUE)
        );
        ContentLayout.setVerticalGroup(
            ContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        Navigation.setBackground(new java.awt.Color(204, 204, 204));

        adminBtn.setBackground(new java.awt.Color(250, 250, 250));
        adminBtn.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        adminBtn.setText("Admin Home");
        adminBtn.setFocusable(false);
        adminBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminBtnActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("SECURITY Svcs");
        jLabel1.setToolTipText("");

        managerBtn.setBackground(new java.awt.Color(250, 250, 250));
        managerBtn.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        managerBtn.setText("Manager Home");
        managerBtn.setFocusable(false);
        managerBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                managerBtnActionPerformed(evt);
            }
        });

        staffBtn.setBackground(new java.awt.Color(250, 250, 250));
        staffBtn.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        staffBtn.setText("Staff Home");
        staffBtn.setFocusable(false);
        staffBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                staffBtnActionPerformed(evt);
            }
        });

        clientBtn.setBackground(new java.awt.Color(250, 250, 250));
        clientBtn.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        clientBtn.setText("Client Home");
        clientBtn.setFocusable(false);
        clientBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clientBtnActionPerformed(evt);
            }
        });

        logoutBtn.setBackground(new java.awt.Color(250, 250, 250));
        logoutBtn.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        logoutBtn.setText("LOGOUT");
        logoutBtn.setFocusable(false);
        logoutBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout NavigationLayout = new javax.swing.GroupLayout(Navigation);
        Navigation.setLayout(NavigationLayout);
        NavigationLayout.setHorizontalGroup(
            NavigationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(NavigationLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(NavigationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(adminBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
                    .addComponent(managerBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(staffBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(clientBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(logoutBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        NavigationLayout.setVerticalGroup(
            NavigationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(NavigationLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(adminBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(managerBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(staffBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(clientBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 139, Short.MAX_VALUE)
                .addComponent(logoutBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout HomePnlLayout = new javax.swing.GroupLayout(HomePnl);
        HomePnl.setLayout(HomePnlLayout);
        HomePnlLayout.setHorizontalGroup(
            HomePnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, HomePnlLayout.createSequentialGroup()
                .addComponent(Navigation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Content, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        HomePnlLayout.setVerticalGroup(
            HomePnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Content, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(Navigation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout ContainerLayout = new javax.swing.GroupLayout(Container);
        Container.setLayout(ContainerLayout);
        ContainerLayout.setHorizontalGroup(
            ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 980, Short.MAX_VALUE)
            .addGroup(ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(HomePnl, javax.swing.GroupLayout.DEFAULT_SIZE, 980, Short.MAX_VALUE))
        );
        ContainerLayout.setVerticalGroup(
            ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
            .addGroup(ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(HomePnl, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Container, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Container, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void adminBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminBtnActionPerformed
        if (checkSessionAndAuthorization(5)) { // Only Admins (role 5) can access
            adminHomePnl.showPnl("home");
            contentView.show(Content, "adminHomePnl");
            logSecurityEvent("NOTICE", currentUser.getUsername(), "Accessed Admin Home");
        }
    }//GEN-LAST:event_adminBtnActionPerformed

    private void managerBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_managerBtnActionPerformed
       if (checkSessionAndAuthorization(4)) { // Managers (role 4) and above can access
            managerHomePnl.showPnl("home");
            contentView.show(Content, "managerHomePnl");
            logSecurityEvent("NOTICE", currentUser.getUsername(), "Accessed Manager Home");
        }
    }//GEN-LAST:event_managerBtnActionPerformed

    private void staffBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_staffBtnActionPerformed
        if (checkSessionAndAuthorization(3)) { // Staff (role 3) and above can access
            staffHomePnl.showPnl("home");
            contentView.show(Content, "staffHomePnl");
            logSecurityEvent("NOTICE", currentUser.getUsername(), "Accessed Staff Home");
        }
    }//GEN-LAST:event_staffBtnActionPerformed

    private void clientBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clientBtnActionPerformed
         if (checkSessionAndAuthorization(2)) { // Clients (role 2) and above can access
            clientHomePnl.showPnl("home");
            contentView.show(Content, "clientHomePnl");
            logSecurityEvent("NOTICE", currentUser.getUsername(), "Accessed Client Home");
        }
    }//GEN-LAST:event_clientBtnActionPerformed

    private void logoutBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutBtnActionPerformed
        performLogout(); 
    }//GEN-LAST:event_logoutBtnActionPerformed

    public Main main;
    public Login loginPnl = new Login();
    public Register registerPnl = new Register();
    
    private AdminHome adminHomePnl = new AdminHome();
    private ManagerHome managerHomePnl = new ManagerHome();
    private StaffHome staffHomePnl = new StaffHome();
    private ClientHome clientHomePnl = new ClientHome();
    
    private CardLayout contentView = new CardLayout();
    private CardLayout frameView = new CardLayout();
    
    public void init(Main controller){
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("CSSECDV - SECURITY Svcs");
        this.setLocationRelativeTo(null);
        
        this.main = controller;
        loginPnl.frame = this;
        registerPnl.frame = this;
        
        adminHomePnl.init(main.sqlite);
        clientHomePnl.init(main.sqlite);
        managerHomePnl.init(main.sqlite);
        staffHomePnl.init(main.sqlite);
        
        Container.setLayout(frameView);
        Container.add(loginPnl, "loginPnl");
        Container.add(registerPnl, "registerPnl");
        Container.add(HomePnl, "homePnl");
        frameView.show(Container, "loginPnl");
        
        Content.setLayout(contentView);
        Content.add(adminHomePnl, "adminHomePnl");
        Content.add(managerHomePnl, "managerHomePnl");
        Content.add(staffHomePnl, "staffHomePnl");
        Content.add(clientHomePnl, "clientHomePnl");
        
         //Initialize buttons properly based on user permissions
        updateNavigationButtons();
        
        this.setVisible(true);
    }
    
     
    // Sets the current user and starts their session - called after successful login
    public void setCurrentUser(User user) {
        this.currentUser = user;
        this.sessionStartTime = System.currentTimeMillis();
        updateNavigationButtons(); // Show/hide buttons based on user role
        logSecurityEvent("NOTICE", user.getUsername(), "User session started");
    }
    
    // Gets who's currently logged in
    public User getCurrentUser() {
        return currentUser;
    }
    
    // The brain of our security system - checks if user can access something
    private boolean checkSessionAndAuthorization(int requiredRole) {
        // First, is anyone even logged in?
        if (currentUser == null) {
            showUnauthorizedMessage("You need to login first! 🔒");
            loginNav();
            return false;
        }
        
        // Has their session expired? (Security timeout)
        if (System.currentTimeMillis() - sessionStartTime > SESSION_TIMEOUT_MS) {
            showSessionExpiredMessage();
            performLogout();
            return false;
        }
        
        // Do they have the right permissions? (Authorization check)
        if (currentUser.getRole() < requiredRole) {
            showUnauthorizedMessage("Access denied! You don't have permission for this area. 🚫");
            logSecurityEvent("WARNING", currentUser.getUsername(), 
                "Tried to access role " + requiredRole + " area but only has role " + currentUser.getRole());
            return false;
        }
        
        return true; // All checks passed!
    }
    
    // Updates which buttons the user can see based on their role (fixes security issue #3)
    private void updateNavigationButtons() {
        if (currentUser == null) {
            // Nobody logged in - disable everything
            adminBtn.setEnabled(false);
            managerBtn.setEnabled(false);
            staffBtn.setEnabled(false);
            clientBtn.setEnabled(false);
            logoutBtn.setEnabled(false);
        } else {
            // Show buttons based on user's role level
            int userRole = currentUser.getRole();
            
            adminBtn.setEnabled(userRole >= 5);     // Only Admins see Admin button
            managerBtn.setEnabled(userRole >= 4);   // Managers and Admins see Manager button
            staffBtn.setEnabled(userRole >= 3);     // Staff, Managers, and Admins see Staff button
            clientBtn.setEnabled(userRole >= 2);    // Everyone (except disabled) sees Client button
            logoutBtn.setEnabled(true);             // Everyone can logout
            
            // Show who's logged in
            jLabel1.setText("Welcome, " + currentUser.getUsername() + "!");
        }
    }
    
    // Smart navigation - takes user to their appropriate home page (fixes security issue #3)
    public void navigateToUserHome(int userRole) {
        switch (userRole) {
            case 5: // Admin - goes to Admin home
                adminHomePnl.showPnl("home");
                contentView.show(Content, "adminHomePnl");
                break;
            case 4: // Manager - goes to Manager home
                managerHomePnl.showPnl("home");
                contentView.show(Content, "managerHomePnl");
                break;
            case 3: // Staff - goes to Staff home
                staffHomePnl.showPnl("home");
                contentView.show(Content, "staffHomePnl");
                break;
            case 2: // Client - goes to Client home
                clientHomePnl.showPnl("home");
                contentView.show(Content, "clientHomePnl");
                break;
            default:
                // Disabled or invalid role - kick them out
                showUnauthorizedMessage("Your account is disabled or has invalid permissions.");
                performLogout();
                return;
        }
        
        frameView.show(Container, "homePnl"); // Show the main application
    }
    
    //Secure logout - cleans up everything properly
    private void performLogout() {
        if (currentUser != null) {
            logSecurityEvent("NOTICE", currentUser.getUsername(), "User logged out");
            currentUser = null; // Clear current user
        }
        
        sessionStartTime = 0; // Reset session time
        updateNavigationButtons(); // Hide all buttons
        jLabel1.setText("SECURITY Svcs"); // Reset welcome message
        
        frameView.show(Container, "loginPnl"); // Go back to login page
    }
    
    // Helper methods for user messages
    private void showUnauthorizedMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Access Denied", JOptionPane.WARNING_MESSAGE);
    }
    
    private void showSessionExpiredMessage() {
        JOptionPane.showMessageDialog(this, 
            "Your session expired for security reasons. Please login again.", 
            "Session Expired", JOptionPane.WARNING_MESSAGE);
    }
    
    // Security logging - keeps track of what users do
    private void logSecurityEvent(String event, String username, String description) {
        try {
            if (main != null && main.sqlite != null) {
                Timestamp timestamp = new Timestamp(new Date().getTime());
                main.sqlite.addLogs(event, username, description, timestamp.toString());
            }
        } catch (Exception e) {
            System.err.println("Failed to log security event: " + e.getMessage());
        }
    }
    
    // Navigation methods (updated for security)
    
    
public void mainNav(){
        if (currentUser != null) {
            navigateToUserHome(currentUser.getRole()); // Go to appropriate home
        } else {
            loginNav(); // Not logged in - go to login
        }
    }
    
    public void loginNav(){
        // Clear any existing session when going to login
        currentUser = null;
        sessionStartTime = 0;
        updateNavigationButtons();
        jLabel1.setText("SECURITY Svcs");
        frameView.show(Container, "loginPnl");
    }
    
    public void registerNav(){
        frameView.show(Container, "registerPnl");
    }
    
    public void registerAction(String username, String password, String confpass){
        main.sqlite.addUser(username, password);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Container;
    private javax.swing.JPanel Content;
    private javax.swing.JPanel HomePnl;
    private javax.swing.JPanel Navigation;
    private javax.swing.JButton adminBtn;
    private javax.swing.JButton clientBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton logoutBtn;
    private javax.swing.JButton managerBtn;
    private javax.swing.JButton staffBtn;
    // End of variables declaration//GEN-END:variables
}


