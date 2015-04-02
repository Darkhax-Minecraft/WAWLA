package net.darkhax.wawla.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is an all in one utility which can be used to create and upload files to
 * pastebin. This utility was originally created by CaptainBern from the bukkit forums. Their
 * original post can be found here: https://bukkit.org/threads/snippet-pastebinreporter.228641/
 * This version of the file has been edited in several ways. The main changes made were
 * formatting and some additional syntax sugar to make it fit in with the rest of the project.
 * In addition to those changes, a few variables such as the new line variable were replaced
 * with the variable from system so that it may work on all operating system.
 */
public class PastebinUtility {
    
    /**
     * A list of all possible syntax' supported by Pastebin.
     */
    public static class ReportFormat {
        
        public static final ReportFormat FOUR_CS = new ReportFormat("4cs");
        public static final ReportFormat SIX_FIVE_ZERO_TWO_ACME_Cross_Assembler = new ReportFormat("6502acme");
        public static final ReportFormat SIX_FIVE_ZERO_TWO_Kick_Assembler = new ReportFormat("6502kickass");
        public static final ReportFormat SIX_FIVE_ZERO_TWO_TASM_64TASS = new ReportFormat("6502tasm");
        public static final ReportFormat ABAP = new ReportFormat("abap");
        public static final ReportFormat ActionScript = new ReportFormat("actionscript");
        public static final ReportFormat ActionScript_3 = new ReportFormat("actionscript3");
        public static final ReportFormat Ada = new ReportFormat("ada");
        public static final ReportFormat ALGOL_68 = new ReportFormat("algol68");
        public static final ReportFormat Apache_Log = new ReportFormat("apache");
        public static final ReportFormat AppleScript = new ReportFormat("applescript");
        public static final ReportFormat APT_Sources = new ReportFormat("apt_sources");
        public static final ReportFormat ARM = new ReportFormat("arm");
        public static final ReportFormat ASM = new ReportFormat("asm");
        public static final ReportFormat ASP = new ReportFormat("asp");
        public static final ReportFormat Asymptote = new ReportFormat("asymptote");
        public static final ReportFormat autoconf = new ReportFormat("autoconf");
        public static final ReportFormat Autohotkey = new ReportFormat("autohotkey");
        public static final ReportFormat AutoIt = new ReportFormat("autoit");
        public static final ReportFormat Avisynth = new ReportFormat("avisynth");
        public static final ReportFormat Awk = new ReportFormat("awk");
        public static final ReportFormat BASCOM_AVR = new ReportFormat("bascomavr");
        public static final ReportFormat Bash = new ReportFormat("bash");
        public static final ReportFormat Basic4GL = new ReportFormat("basic4gl");
        public static final ReportFormat BibTeX = new ReportFormat("bibtex");
        public static final ReportFormat Blitz_Basic = new ReportFormat("blitzbasic");
        public static final ReportFormat BNF = new ReportFormat("bnf");
        public static final ReportFormat BOO = new ReportFormat("boo");
        public static final ReportFormat BrainFuck = new ReportFormat("bf");
        public static final ReportFormat C = new ReportFormat("c");
        public static final ReportFormat C_for_Macs = new ReportFormat("c_mac");
        public static final ReportFormat C_Intermediate_Language = new ReportFormat("cil");
        public static final ReportFormat C_SHARP = new ReportFormat("csharp");
        public static final ReportFormat C_PP = new ReportFormat("cpp");
        public static final ReportFormat CPP_QT = new ReportFormat("cpp-qt");
        public static final ReportFormat C_Loadrunner = new ReportFormat("c_loadrunner");
        public static final ReportFormat CAD_DCL = new ReportFormat("caddcl");
        public static final ReportFormat CAD_Lisp = new ReportFormat("cadlisp");
        public static final ReportFormat CFDG = new ReportFormat("cfdg");
        public static final ReportFormat ChaiScript = new ReportFormat("chaiscript");
        public static final ReportFormat Clojure = new ReportFormat("clojure");
        public static final ReportFormat Clone_C = new ReportFormat("klonec");
        public static final ReportFormat Clone_C_PP = new ReportFormat("klonecpp");
        public static final ReportFormat CMake = new ReportFormat("cmake");
        public static final ReportFormat COBOL = new ReportFormat("cobol");
        public static final ReportFormat CoffeeScript = new ReportFormat("coffeescript");
        public static final ReportFormat ColdFusion = new ReportFormat("cfm");
        public static final ReportFormat CSS = new ReportFormat("css");
        public static final ReportFormat Cuesheet = new ReportFormat("cuesheet");
        public static final ReportFormat D = new ReportFormat("d");
        public static final ReportFormat DCL = new ReportFormat("dcl");
        public static final ReportFormat DCPU_16 = new ReportFormat("dcpu16");
        public static final ReportFormat DCS = new ReportFormat("dcs");
        public static final ReportFormat Delphi = new ReportFormat("delphi");
        public static final ReportFormat Delphi_Prism_Oxygene = new ReportFormat("oxygene");
        public static final ReportFormat Diff = new ReportFormat("diff");
        public static final ReportFormat DIV = new ReportFormat("div");
        public static final ReportFormat DOS = new ReportFormat("dos");
        public static final ReportFormat DOT = new ReportFormat("dot");
        public static final ReportFormat E = new ReportFormat("e");
        public static final ReportFormat ECMAScript = new ReportFormat("ecmascript");
        public static final ReportFormat Eiffel = new ReportFormat("eiffel");
        public static final ReportFormat Email = new ReportFormat("email");
        public static final ReportFormat EPC = new ReportFormat("epc");
        public static final ReportFormat Erlang = new ReportFormat("erlang");
        public static final ReportFormat F_SHARP = new ReportFormat("fsharp");
        public static final ReportFormat Falcon = new ReportFormat("falcon");
        public static final ReportFormat FO_Language = new ReportFormat("fo");
        public static final ReportFormat Formula_One = new ReportFormat("f1");
        public static final ReportFormat Fortran = new ReportFormat("fortran");
        public static final ReportFormat FreeBasic = new ReportFormat("freebasic");
        public static final ReportFormat FreeSWITCH = new ReportFormat("freeswitch");
        public static final ReportFormat GAMBAS = new ReportFormat("gambas");
        public static final ReportFormat Game_Maker = new ReportFormat("gml");
        public static final ReportFormat GDB = new ReportFormat("gdb");
        public static final ReportFormat Genero = new ReportFormat("genero");
        public static final ReportFormat Genie = new ReportFormat("genie");
        public static final ReportFormat GetText = new ReportFormat("gettext");
        public static final ReportFormat Go = new ReportFormat("go");
        public static final ReportFormat Groovy = new ReportFormat("groovy");
        public static final ReportFormat GwBasic = new ReportFormat("gwbasic");
        public static final ReportFormat Haskell = new ReportFormat("haskell");
        public static final ReportFormat Haxe = new ReportFormat("haxe");
        public static final ReportFormat HicEst = new ReportFormat("hicest");
        public static final ReportFormat HQ9_Plus = new ReportFormat("hq9plus");
        public static final ReportFormat HTML = new ReportFormat("html4strict");
        public static final ReportFormat HTML_5 = new ReportFormat("html5");
        public static final ReportFormat Icon = new ReportFormat("icon");
        public static final ReportFormat IDL = new ReportFormat("idl");
        public static final ReportFormat INI_file = new ReportFormat("ini");
        public static final ReportFormat Inno_Script = new ReportFormat("inno");
        public static final ReportFormat INTERCAL = new ReportFormat("intercal");
        public static final ReportFormat IO = new ReportFormat("io");
        public static final ReportFormat J = new ReportFormat("j");
        public static final ReportFormat Java = new ReportFormat("java");
        public static final ReportFormat Java_5 = new ReportFormat("java5");
        public static final ReportFormat JavaScript = new ReportFormat("javascript");
        public static final ReportFormat jQuery = new ReportFormat("jquery");
        public static final ReportFormat KiXtart = new ReportFormat("kixtart");
        public static final ReportFormat Latex = new ReportFormat("latex");
        public static final ReportFormat LDIF = new ReportFormat("ldif");
        public static final ReportFormat Liberty_BASIC = new ReportFormat("lb");
        public static final ReportFormat Linden_Scripting = new ReportFormat("lsl2");
        public static final ReportFormat Lisp = new ReportFormat("lisp");
        public static final ReportFormat LLVM = new ReportFormat("llvm");
        public static final ReportFormat Loco_Basic = new ReportFormat("locobasic");
        public static final ReportFormat Logtalk = new ReportFormat("logtalk");
        public static final ReportFormat LOL_Code = new ReportFormat("lolcode");
        public static final ReportFormat Lotus_Formulas = new ReportFormat("lotusformulas");
        public static final ReportFormat Lotus_Script = new ReportFormat("lotusscript");
        public static final ReportFormat LScript = new ReportFormat("lscript");
        public static final ReportFormat Lua = new ReportFormat("lua");
        public static final ReportFormat M68000_Assembler = new ReportFormat("m68k");
        public static final ReportFormat MagikSF = new ReportFormat("magiksf");
        public static final ReportFormat Make = new ReportFormat("make");
        public static final ReportFormat MapBasic = new ReportFormat("mapbasic");
        public static final ReportFormat MatLab = new ReportFormat("matlab");
        public static final ReportFormat mIRC = new ReportFormat("mirc");
        public static final ReportFormat MIX_Assembler = new ReportFormat("mmix");
        public static final ReportFormat Modula_2 = new ReportFormat("modula2");
        public static final ReportFormat Modula_3 = new ReportFormat("modula3");
        public static final ReportFormat Motorola_68000_HiSoft_Dev = new ReportFormat("68000devpac");
        public static final ReportFormat MPASM = new ReportFormat("mpasm");
        public static final ReportFormat MXML = new ReportFormat("mxml");
        public static final ReportFormat MySQL = new ReportFormat("mysql");
        public static final ReportFormat Nagios = new ReportFormat("nagios");
        public static final ReportFormat newLISP = new ReportFormat("newlisp");
        public static final ReportFormat PLAIN_TEXT = new ReportFormat("text");
        public static final ReportFormat NullSoft_Installer = new ReportFormat("nsis");
        public static final ReportFormat Oberon_2 = new ReportFormat("oberon2");
        public static final ReportFormat Objeck_Programming_Langua = new ReportFormat("objeck");
        public static final ReportFormat Objective_C = new ReportFormat("objc");
        public static final ReportFormat OCalm_Brief = new ReportFormat("ocaml-brief");
        public static final ReportFormat OCaml = new ReportFormat("ocaml");
        public static final ReportFormat Octave = new ReportFormat("octave");
        public static final ReportFormat OpenBSD_PACKET_FILTER = new ReportFormat("pf");
        public static final ReportFormat OpenGL_Shading = new ReportFormat("glsl");
        public static final ReportFormat Openoffice_BASIC = new ReportFormat("oobas");
        public static final ReportFormat Oracle_11 = new ReportFormat("oracle11");
        public static final ReportFormat Oracle_8 = new ReportFormat("oracle8");
        public static final ReportFormat Oz = new ReportFormat("oz");
        public static final ReportFormat ParaSail = new ReportFormat("parasail");
        public static final ReportFormat PARI_GP = new ReportFormat("parigp");
        public static final ReportFormat Pascal = new ReportFormat("pascal");
        public static final ReportFormat PAWN = new ReportFormat("pawn");
        public static final ReportFormat PCRE = new ReportFormat("pcre");
        public static final ReportFormat Per = new ReportFormat("per");
        public static final ReportFormat Perl = new ReportFormat("perl");
        public static final ReportFormat Perl_6 = new ReportFormat("perl6");
        public static final ReportFormat PHP = new ReportFormat("php");
        public static final ReportFormat PHP_Brief = new ReportFormat("php-brief");
        public static final ReportFormat Pic_16 = new ReportFormat("pic16");
        public static final ReportFormat Pike = new ReportFormat("pike");
        public static final ReportFormat Pixel_Bender = new ReportFormat("pixelbender");
        public static final ReportFormat PL_SQL = new ReportFormat("plsql");
        public static final ReportFormat PostgreSQL = new ReportFormat("postgresql");
        public static final ReportFormat POV_Ray = new ReportFormat("povray");
        public static final ReportFormat Power_Shell = new ReportFormat("powershell");
        public static final ReportFormat PowerBuilder = new ReportFormat("powerbuilder");
        public static final ReportFormat ProFTPd = new ReportFormat("proftpd");
        public static final ReportFormat Progress = new ReportFormat("progress");
        public static final ReportFormat Prolog = new ReportFormat("prolog");
        public static final ReportFormat Properties = new ReportFormat("properties");
        public static final ReportFormat ProvideX = new ReportFormat("providex");
        public static final ReportFormat PureBasic = new ReportFormat("purebasic");
        public static final ReportFormat PyCon = new ReportFormat("pycon");
        public static final ReportFormat Python = new ReportFormat("python");
        public static final ReportFormat Python_for_S60 = new ReportFormat("pys60");
        public static final ReportFormat q_kdb_PLUS = new ReportFormat("q");
        public static final ReportFormat QBasic = new ReportFormat("qbasic");
        public static final ReportFormat R = new ReportFormat("rsplus");
        public static final ReportFormat Rails = new ReportFormat("rails");
        public static final ReportFormat REBOL = new ReportFormat("rebol");
        public static final ReportFormat REG = new ReportFormat("reg");
        public static final ReportFormat Rexx = new ReportFormat("rexx");
        public static final ReportFormat Robots = new ReportFormat("robots");
        public static final ReportFormat RPM_Spec = new ReportFormat("rpmspec");
        public static final ReportFormat Ruby = new ReportFormat("ruby");
        public static final ReportFormat Ruby_Gnuplot = new ReportFormat("gnuplot");
        public static final ReportFormat SAS = new ReportFormat("sas");
        public static final ReportFormat Scala = new ReportFormat("scala");
        public static final ReportFormat Scheme = new ReportFormat("scheme");
        public static final ReportFormat Scilab = new ReportFormat("scilab");
        public static final ReportFormat SdlBasic = new ReportFormat("sdlbasic");
        public static final ReportFormat Smalltalk = new ReportFormat("smalltalk");
        public static final ReportFormat Smarty = new ReportFormat("smarty");
        public static final ReportFormat SPARK = new ReportFormat("spark");
        public static final ReportFormat SPARQL = new ReportFormat("sparql");
        public static final ReportFormat SQL = new ReportFormat("sql");
        public static final ReportFormat StoneScript = new ReportFormat("stonescript");
        public static final ReportFormat SystemVerilog = new ReportFormat("systemverilog");
        public static final ReportFormat T_SQL = new ReportFormat("tsql");
        public static final ReportFormat TCL = new ReportFormat("tcl");
        public static final ReportFormat Tera_Term = new ReportFormat("teraterm");
        public static final ReportFormat thinBasic = new ReportFormat("thinbasic");
        public static final ReportFormat TypoScript = new ReportFormat("typoscript");
        public static final ReportFormat Unicon = new ReportFormat("unicon");
        public static final ReportFormat UnrealScript = new ReportFormat("uscript");
        public static final ReportFormat UPC = new ReportFormat("ups");
        public static final ReportFormat Urbi = new ReportFormat("urbi");
        public static final ReportFormat Vala = new ReportFormat("vala");
        public static final ReportFormat VB_DOT_NET = new ReportFormat("vbnet");
        public static final ReportFormat Vedit = new ReportFormat("vedit");
        public static final ReportFormat VeriLog = new ReportFormat("verilog");
        public static final ReportFormat VHDL = new ReportFormat("vhdl");
        public static final ReportFormat VIM = new ReportFormat("vim");
        public static final ReportFormat Visual_Pro_Log = new ReportFormat("visualprolog");
        public static final ReportFormat VisualBasic = new ReportFormat("vb");
        public static final ReportFormat VisualFoxPro = new ReportFormat("visualfoxpro");
        public static final ReportFormat WhiteSpace = new ReportFormat("whitespace");
        public static final ReportFormat WHOIS = new ReportFormat("whois");
        public static final ReportFormat Winbatch = new ReportFormat("winbatch");
        public static final ReportFormat XBasic = new ReportFormat("xbasic");
        public static final ReportFormat XML = new ReportFormat("xml");
        public static final ReportFormat Xorg_Config = new ReportFormat("xorg_conf");
        public static final ReportFormat XPP = new ReportFormat("xpp");
        public static final ReportFormat YAML = new ReportFormat("yaml");
        public static final ReportFormat Z80_Assembler = new ReportFormat("z80");
        public static final ReportFormat ZXBasic = new ReportFormat("zxbasic");
        
        private final String FORMAT;
        
        /**
         * Creates a new format code to be used by the application.
         * 
         * @param format: The format code being used.
         */
        public ReportFormat(String format) {
        
            this.FORMAT = format;
        }
        
        @Override
        public String toString () {
        
            return this.FORMAT;
        }
    }
    
    /**
     * The possible expiration dates supported by Pastebin.
     */
    public static enum ExpireDate {
        
        NEVER("N"), TEN_MINUTES("10M"), ONE_HOUR("1H"), ONE_DAY("1D"), ONE_WEEK("1W"), TWO_WEEKS("2W"), ONE_MONTH("1M");
        
        private final String key;
        
        /**
         * Creates a new possible expiration date.
         * 
         * @param keyValue: The format code for the expiration date.
         */
        ExpireDate(String keyValue) {
        
            this.key = keyValue;
        }
        
        @Override
        public String toString () {
        
            return this.key;
        }
    }
    
    /**
     * The Paste, this will be the file submitted to pastebin.
     */
    public static class Paste {
        
        private final Paste INSTANCE;
        private String HEADER;
        private String NEW_LINE = System.getProperty("line.separator");
        private List<String> TEXT = new ArrayList<String>();
        
        public Paste() {
        
            INSTANCE = this;
        }
        
        public Paste(String header) {
        
            this();
            this.HEADER = header;
        }
        
        public Paste(File file) {
        
            this(file, false);
        }
        
        public Paste(final File file, boolean async) {
        
            this();
            
            try {
                
                if (async) {
                    
                    Runnable runnable = new Runnable() {
                        
                        @Override
                        public void run () {
                        
                            scanFile(file);
                        }
                    };
                    
                    new Thread(runnable).start();
                }
                
                else {
                    
                    scanFile(file);
                }
                
            }
            
            catch (Exception e) {
                
                e.printStackTrace();
            }
        }
        
        private void scanFile (File file) {
        
            try {
                
                String line;
                BufferedReader reader = new BufferedReader(new FileReader(file));
                
                while ((line = reader.readLine()) != null)
                    TEXT.add(line);
                
                reader.close();
            }
            
            catch (Exception e) {
                
                e.printStackTrace();
            }
        }
        
        public Paste appendLine (String string) {
        
            this.TEXT.add(string);
            return this.INSTANCE;
        }
        
        public Paste appendLine (int index, String string) {
        
            this.TEXT.add(index, string);
            return this.INSTANCE;
        }
        
        @Override
        public String toString () {
        
            StringBuilder builder = new StringBuilder();
            
            if (HEADER != null)
                builder.append(HEADER);
            
            for (String line : TEXT)
                builder.append(line + NEW_LINE);
            
            return builder.toString();
        }
    }
    
    private final String POST_URL = "http://pastebin.com/api/api_post.php";
    
    private String API_KEY;
    
    public PastebinUtility(String apiKey) {
    
        this.API_KEY = apiKey;
    }
    
    public String post (String name, Paste paste, ReportFormat format, ExpireDate expireDate) {
    
        if (name == null)
            name = "";
        
        String report_url = "";
        
        try {
            
            URL urls = new URL(this.POST_URL);
            HttpURLConnection conn = (HttpURLConnection) urls.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setRequestMethod("POST");
            conn.addRequestProperty("Content-type", "application/x-www-form-urlencoded");
            conn.setInstanceFollowRedirects(false);
            conn.setDoOutput(true);
            OutputStream out = conn.getOutputStream();
            
            byte[] data = ("api_option=paste" + "&api_dev_key=" + URLEncoder.encode(this.API_KEY, "utf-8") + "&api_paste_code=" + URLEncoder.encode(paste.toString(), "utf-8") + "&api_paste_private=" + URLEncoder.encode("1", "utf-8") + "&api_paste_name=" + URLEncoder.encode(name, "utf-8") + "&api_paste_expire_date=" + URLEncoder.encode(expireDate.toString(), "utf-8") + "&api_paste_format=" + URLEncoder.encode(format.toString(), "utf-8") + "&api_user_key=" + URLEncoder.encode("", "utf-8")).getBytes();
            
            out.write(data);
            out.flush();
            out.close();
            
            if (conn.getResponseCode() == 200) {
                
                InputStream receive = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(receive));
                String line;
                StringBuffer response = new StringBuffer();
                
                while ((line = reader.readLine()) != null) {
                    
                    response.append(line);
                    response.append("\r\n");
                }
                
                reader.close();
                
                String result = response.toString().trim();
                
                if (!result.contains("http://"))
                    report_url = "Failed to post! (returned result: " + result;
                
                else
                    report_url = result.trim();
            }
            
            else
                report_url = "Failed to post!";
        }
        
        catch (Exception e) {
            
            e.printStackTrace();
        }
        
        return report_url;
    }
}