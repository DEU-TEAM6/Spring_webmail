/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.cse.spring_webmail.control;

import deu.cse.spring_webmail.model.Addkeyword;
import deu.cse.spring_webmail.model.AddkeywordRow;
import deu.cse.spring_webmail.model.Pop3Agent;
import deu.cse.spring_webmail.model.TrashCanManager;
import deu.cse.spring_webmail.model.TrashCanRow;
import deu.cse.spring_webmail.model.UserAdminAgent;
import deu.cse.spring_webmail.model.loadDB;
import deu.cse.spring_webmail.model.AddrBookRow;
import deu.cse.spring_webmail.model.AddrBookManager;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 초기 화면과 관리자 기능(사용자 추가, 삭제)에 대한 제어기
 *
 * @author skylo
 */
@Controller
@PropertySource("classpath:/system.properties")
@Slf4j
public class SystemController {

    @Autowired
    private ServletContext ctx;
    @Autowired
    private HttpSession session;
    @Autowired
    private HttpServletRequest request;

    @Value("${root.id}")
    private String ROOT_ID;
    @Value("${root.password}")
    private String ROOT_PASSWORD;
    @Value("${admin.id}")
    private String ADMINISTRATOR;  //  = "admin";
    @Value("${james.control.port}")
    private Integer JAMES_CONTROL_PORT;
    @Value("${james.host}")
    private String JAMES_HOST;

    @GetMapping("/")
    public String index() {
        log.debug("index() called...");
        session.setAttribute("host", JAMES_HOST);
        session.setAttribute("debug", "false");

        return "/index";
    }

    @RequestMapping(value = "/login.do", method = {RequestMethod.GET, RequestMethod.POST})
    public String loginDo(@RequestParam Integer menu) {
        String url = "";
        log.debug("로그인 처리: menu = {}", menu);
        switch (menu) {
            case CommandType.LOGIN:
                String host = (String) request.getSession().getAttribute("host");
                String userid = request.getParameter("userid");
                String password = request.getParameter("passwd");

                // Check the login information is valid using <<model>>Pop3Agent.
                Pop3Agent pop3Agent = new Pop3Agent(host, userid, password);
                boolean isLoginSuccess = pop3Agent.validate();

                // Now call the correct page according to its validation result.
                if (isLoginSuccess) {
                    if (isAdmin(userid)) {
                        // HttpSession 객체에 userid를 등록해 둔다.
                        session.setAttribute("userid", userid);
                        // response.sendRedirect("admin_menu.jsp");
                        url = "redirect:/admin_menu";
                    } else {
                        // HttpSession 객체에 userid와 password를 등록해 둔다.
                        session.setAttribute("userid", userid);
                        session.setAttribute("password", password);
                        // response.sendRedirect("main_menu.jsp");
                        url = "redirect:/main_menu";  // URL이 http://localhost:8080/webmail/main_menu 이와 같이 됨.
                        // url = "/main_menu";  // URL이 http://localhost:8080/webmail/login.do?menu=91 이와 같이 되어 안 좋음
                    }
                } else {
                    // RequestDispatcher view = request.getRequestDispatcher("login_fail.jsp");
                    // view.forward(request, response);
                    url = "redirect:/login_fail";
                }
                break;
            case CommandType.LOGOUT:
                session.invalidate();
                url = "redirect:/";  // redirect: 반드시 넣어야만 컨텍스트 루트로 갈 수 있음
                break;
            default:
                break;
        }
        return url;
    }

    @GetMapping("/login_fail")
    public String loginFail() {
        return "login_fail";
    }

    protected boolean isAdmin(String userid) {
        boolean status = false;

        if (userid.equals(this.ADMINISTRATOR)) {
            status = true;
        }

        return status;
    }

    @GetMapping("/main_menu")
    public String mainmenu(Model model) {
        Pop3Agent pop3 = new Pop3Agent();
        pop3.setHost((String) session.getAttribute("host"));
        pop3.setUserid((String) session.getAttribute("userid"));
        pop3.setPassword((String) session.getAttribute("password"));

        String messageList = pop3.getMessageList(0);
        model.addAttribute("messageList", messageList);
        return "main_menu";
    }

    @GetMapping("/trash_can")
    public String trash_can(Model model) {
        String userid = (String) session.getAttribute("userid");

        String url = loadDB.getInstance().getUrl();
        String id = loadDB.getInstance().getId();
        String pw = loadDB.getInstance().getPw();
        String driver = loadDB.getInstance().getDriver();
        TrashCanManager manager = new TrashCanManager(url, id, pw, driver);
        List<TrashCanRow> dataRows = manager.getAllRows(userid);
        model.addAttribute("trashcanList", dataRows);
        return "trash_can";
    }

    @GetMapping("/admin_menu")
    public String adminMenu(Model model) {
        log.debug("root.id = {}, root.password = {}, admin.id = {}",
                ROOT_ID, ROOT_PASSWORD, ADMINISTRATOR);

        model.addAttribute("userList", getUserList());
        return "admin/admin_menu";
    }

    @GetMapping("/add_user")
    public String addUser() {
        return "admin/add_user";
    }

    @PostMapping("/add_user.do")
    public String addUserDo(@RequestParam String id, @RequestParam String password,
            RedirectAttributes attrs) {
        log.debug("add_user.do: id = {}, password = {}, port = {}",
                id, password, JAMES_CONTROL_PORT);

        try {
            String cwd = ctx.getRealPath(".");
            UserAdminAgent agent = new UserAdminAgent(JAMES_HOST, JAMES_CONTROL_PORT, cwd,
                    ROOT_ID, ROOT_PASSWORD, ADMINISTRATOR);

            // if (addUser successful)  사용자 등록 성공 팦업창
            // else 사용자 등록 실패 팝업창
            if (agent.addUser(id, password)) {
                attrs.addFlashAttribute("msg", String.format("사용자(%s) 추가를 성공하였습니다.", id));
            } else {
                attrs.addFlashAttribute("msg", String.format("사용자(%s) 추가를 실패하였습니다.", id));
            }
        } catch (Exception ex) {
            log.error("add_user.do: 시스템 접속에 실패했습니다. 예외 = {}", ex.getMessage());
        }

        return "redirect:/admin_menu";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register.do")
    public String registerDo(@RequestParam String id, @RequestParam String password, @RequestParam String passwordcheck,
            RedirectAttributes attrs) {
        log.debug("register.do: id = {}, password = {}, port = {}",
                id, password, JAMES_CONTROL_PORT);
        try {
            String cwd = ctx.getRealPath(".");
            UserAdminAgent agent = new UserAdminAgent(JAMES_HOST, JAMES_CONTROL_PORT, cwd,
                    ROOT_ID, ROOT_PASSWORD, ADMINISTRATOR);

            // if (addUser successful)  사용자 등록 성공 팦업창
            // else 사용자 등록 실패 팝업창
            if (agent.addUser(id, password)) {
                attrs.addFlashAttribute("msg", String.format("사용자(%s)님 회원가입을 성공하였습니다.", id));
            } else {
                attrs.addFlashAttribute("msg", String.format("사용자(%s)님 회원가입을 실패하였습니다.", id));
            }
        } catch (Exception ex) {
            log.error("register.do: 시스템 접속에 실패했습니다. 예외 = {}", ex.getMessage());
        }

        return "redirect:/";
    }

    @GetMapping("changePw")
    public String changePw() {
        return "change_password";
    }

    @PostMapping("changePw.do")
    public String changePwDo(@RequestParam String id, @RequestParam String oldpassword, @RequestParam String password, RedirectAttributes attrs) {
        String path = "";
        String sessionPW = (String) session.getAttribute("password");

        log.debug("register.do: id = {}, password = {}, port = {}",
                id, password, JAMES_CONTROL_PORT);
        try {
            String cwd = ctx.getRealPath(".");
            UserAdminAgent agent = new UserAdminAgent(JAMES_HOST, JAMES_CONTROL_PORT, cwd,
                    ROOT_ID, ROOT_PASSWORD, ADMINISTRATOR);
            if (!sessionPW.equals(oldpassword)) { // 현재 비밀번호가 일치하지 않았을 경우
                attrs.addFlashAttribute("msg", String.format("사용자(%s)님 기존 비밀번호가 일치하지 않습니다. 다시 확인해주세요.", id));
                path = "changePw";
            } else {// 현재 비밀번호가 일치한 경우
                if (agent.changePw(id, password)) { // 비밀번호가 정상적으로 변경된 경우
                    attrs.addFlashAttribute("msg", String.format("사용자(%s)님 비밀번호 변경을 성공하였습니다. 다시 로그인해주세요.", id));
                } else { // 비밀번호 변경이 되지 않았을 경우
                    attrs.addFlashAttribute("msg", String.format("사용자(%s)님 비밀번호 변경을 실패하였습니다.", id));
                    path = "changePw";
                }
            }
        } catch (Exception ex) {
            log.error("changePw.do : 예외 = {}", ex);
        }
        return "redirect:/" + path;
    }

    @PostMapping("/search")
    public String search(Model model, @RequestParam String chk_info, @RequestParam String searchWord) {
        Pop3Agent pop3 = new Pop3Agent();
        pop3.setHost((String) session.getAttribute("host"));
        pop3.setUserid((String) session.getAttribute("userid"));
        pop3.setPassword((String) session.getAttribute("password"));

        String searchList = pop3.getSearchList(chk_info, searchWord);
        System.out.println(chk_info);
        model.addAttribute("searchList", searchList);
        model.addAttribute("chk_info", chk_info);
        model.addAttribute("searchWord", searchWord);
        return "search";
    }

    @GetMapping("/spam_mail")
    public String spam_mail(Model model) {
        Pop3Agent pop3 = new Pop3Agent();
        pop3.setHost((String) session.getAttribute("host"));
        pop3.setUserid((String) session.getAttribute("userid"));
        pop3.setPassword((String) session.getAttribute("password"));

        String messageList = pop3.getMessageList(2);
        model.addAttribute("messageList", messageList);
        return "spam_mail";
    }

    @GetMapping("/insertkeyword")
    public String insertkeyword(Model model) { // 스팸 키워드 목록 보여주기
        String userid = (String) session.getAttribute("userid");
        String url = loadDB.getInstance().getUrl();
        String id = loadDB.getInstance().getId();
        String pw = loadDB.getInstance().getPw();
        String driver = loadDB.getInstance().getDriver();
        Addkeyword addkey = new Addkeyword(url, id, pw, driver); //List<Addkeyword> getAllRows(String userid)
        List<AddkeywordRow> dataList = addkey.getAllRows(userid);
        model.addAttribute("keywordlist", dataList);
        return "insertkeyword";
    }

    @PostMapping("/addkeyword.do")
    public String addkeyword(Model model, @RequestParam String keyword) { //스팸 키워드 추가하기
        String userid = (String) session.getAttribute("userid");
        String url = loadDB.getInstance().getUrl();
        String id = loadDB.getInstance().getId();
        String pw = loadDB.getInstance().getPw();
        String driver = loadDB.getInstance().getDriver();
        Addkeyword addkey = new Addkeyword(url, id, pw, driver);
        addkey.insertkeyword(userid, keyword);
        return "redirect:/insertkeyword";
    }

    @GetMapping("/deletekeyword.do")
    public String deletekeyword(Model model, @RequestParam("keyword") String keyword) { // 스팸 키워드 지우기
        String userid = (String) session.getAttribute("userid");
        String url = loadDB.getInstance().getUrl();
        String id = loadDB.getInstance().getId();
        String pw = loadDB.getInstance().getPw();
        String driver = loadDB.getInstance().getDriver();
        Addkeyword addkey = new Addkeyword(url, id, pw, driver); //List<Addkeyword> getAllRows(String userid)
        addkey.deletekeyword(userid, keyword);
        return "redirect:/insertkeyword";
    }

    @GetMapping("/delete_user")
    public String deleteUser(Model model) {
        log.debug("delete_user called");
        model.addAttribute("userList", getUserList());
        return "admin/delete_user";
    }

    /**
     *
     * @param selectedUsers <input type=checkbox> 필드의 선택된 이메일 ID. 자료형: String[]
     * @param attrs
     * @return
     */
    @PostMapping("delete_user.do")
    public String deleteUserDo(@RequestParam String[] selectedUsers, RedirectAttributes attrs) {
        log.debug("delete_user.do: selectedUser = {}", List.of(selectedUsers));

        try {
            String cwd = ctx.getRealPath(".");
            UserAdminAgent agent = new UserAdminAgent(JAMES_HOST, JAMES_CONTROL_PORT, cwd,
                    ROOT_ID, ROOT_PASSWORD, ADMINISTRATOR);
            agent.deleteUsers(selectedUsers);  // 수정!!!
        } catch (Exception ex) {
            log.error("delete_user.do : 예외 = {}", ex);
        }

        return "redirect:/admin_menu";
    }

    private List<String> getUserList() {
        String cwd = ctx.getRealPath(".");
        UserAdminAgent agent = new UserAdminAgent(JAMES_HOST, JAMES_CONTROL_PORT, cwd,
                ROOT_ID, ROOT_PASSWORD, ADMINISTRATOR);
        List<String> userList = agent.getUserList();
        log.debug("userList = {}", userList);

        //(주의) root.id와 같이 '.'을 넣으면 안 됨.
        userList.sort((e1, e2) -> e1.compareTo(e2));
        return userList;
    }

    @GetMapping("/me_mail")
    public String me_mail() {
        return "me_mail/me_mail";
    }

    @GetMapping("/me_mail_menu")
    public String me_mail_menu(Model model) {
        Pop3Agent pop3 = new Pop3Agent();
        pop3.setHost((String) session.getAttribute("host"));
        pop3.setUserid((String) session.getAttribute("userid"));
        pop3.setPassword((String) session.getAttribute("password"));

        String meMessageList = pop3.getMessageList(1);
        model.addAttribute("meMessageList", meMessageList);
        return "me_mail_menu";
    }

    @GetMapping("/img_test")
    public String imgTest() {
        return "img_test/img_test";
    }

    @GetMapping("/address")
    public String address(Model model) {
        String userid = (String) session.getAttribute("userid");
        String url = loadDB.getInstance().getUrl();
        String id = loadDB.getInstance().getId();
        String pw = loadDB.getInstance().getPw();
        String driver = loadDB.getInstance().getDriver();
        AddrBookManager addrbook = new AddrBookManager(url, id, pw, driver);
        List<AddrBookRow> dataList = addrbook.getAllRows(userid);
        model.addAttribute("addrbooklist", dataList);
        return "address";
    }

    @GetMapping("/insert_address")
    public String insert_address() {
        return "insert_address";
    }

    @PostMapping("insert_address.do")
    public String insert_addressDo(Model model, @RequestParam String name, @RequestParam String note,RedirectAttributes attrs) {
        String userid = (String) session.getAttribute("userid");
        String url = loadDB.getInstance().getUrl();
        String id = loadDB.getInstance().getId();
        String pw = loadDB.getInstance().getPw();
        String driver = loadDB.getInstance().getDriver();
        AddrBookManager addrbook = new AddrBookManager(url, id, pw, driver);
        boolean check = addrbook.searchuser(name);
        if (userid.equals(name) == false && check == true) {
            addrbook.insertaddrbook(userid, name, note);
        } else {
            attrs.addFlashAttribute("msg", "없는 주소록입니다.");
        }
        return "redirect:/address";
    }

    @GetMapping("/deleteaddress.do")
    public String deleteaddress(Model model, @RequestParam("adduser") String adduser) { // 스팸 키워드 지우기
        String userid = (String) session.getAttribute("userid");
        String url = loadDB.getInstance().getUrl();
        String id = loadDB.getInstance().getId();
        String pw = loadDB.getInstance().getPw();
        String driver = loadDB.getInstance().getDriver();
        AddrBookManager addrbook = new AddrBookManager(url, id, pw, driver);
        addrbook.deleteaddrbook(userid, adduser);
        return "redirect:/address";
    }

    @GetMapping("/address_mail")
    public String addressmail(Model model, @RequestParam("adduser") String adduser) { // 사용자 클릭시 메일보기
        Pop3Agent pop3 = new Pop3Agent();
        pop3.setHost((String) session.getAttribute("host"));
        pop3.setUserid((String) session.getAttribute("userid"));
        pop3.setPassword((String) session.getAttribute("password"));

        String addressList = pop3.getAddressList(adduser);
        model.addAttribute("addressList", addressList);
        return "address_mail";
    }

    /**
     * https://34codefactory.wordpress.com/2019/06/16/how-to-display-image-in-jsp-using-spring-code-factory/
     *
     * @param imageName
     * @return
     */
    @RequestMapping(value = "/get_image/{imageName}")
    @ResponseBody
    public byte[] getImage(@PathVariable String imageName) {
        try {
            String folderPath = ctx.getRealPath("/WEB-INF/views/img_test/img");
            return getImageBytes(folderPath, imageName);
        } catch (Exception e) {
            log.error("/get_image 예외: {}", e.getMessage());
        }
        return new byte[0];
    }

    private byte[] getImageBytes(String folderPath, String imageName) {
        ByteArrayOutputStream byteArrayOutputStream;
        BufferedImage bufferedImage;
        byte[] imageInByte;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            bufferedImage = ImageIO.read(new File(folderPath + File.separator + imageName));
            String format = imageName.substring(imageName.lastIndexOf(".") + 1);
            ImageIO.write(bufferedImage, format, byteArrayOutputStream);
            byteArrayOutputStream.flush();
            imageInByte = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
            return imageInByte;
        } catch (FileNotFoundException e) {
            log.error("getImageBytes 예외: {}", e.getMessage());
        } catch (Exception e) {
            log.error("getImageBytes 예외: {}", e.getMessage());
        }
        return null;
    }

    private void alert(String string) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
