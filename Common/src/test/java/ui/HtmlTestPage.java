package ui;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Utility to materialize a small local HTML test page (and a second page) on disk
 * so browser-based tests can run without external network.
 */
public final class HtmlTestPage {
    private HtmlTestPage() {}

    public static class Pages {
        public final Path page1;
        public final Path page2;

        public Pages(Path page1, Path page2) {
            this.page1 = page1;
            this.page2 = page2;
        }
    }

    public static Pages create() {
        try {
            Path dir = Files.createTempDirectory("ui-test-pages");
            Path page2 = dir.resolve("page2.html");
            String html2 = "<!doctype html>\n" +
                    "<html><head><meta charset='utf-8'><title>Second Page</title></head>" +
                    "<body>\n" +
                    "  <h1 id='second-title'>Page Two</h1>\n" +
                    "</body></html>";
            Files.write(page2, html2.getBytes(StandardCharsets.UTF_8));

            Path page1 = dir.resolve("page1.html");
            String html1 = "<!doctype html>\n" +
                    "<html><head><meta charset='utf-8'><title>Test Page</title>\n" +
                    "<style>\n" +
                    "#hoverTarget:hover + #hoverResult { display:block; }\n" +
                    "#hoverResult { display:none; }\n" +
                    "#delayed { display:none; }\n" +
                    "#spacer { height: 1500px; }\n" +
                    "</style>\n" +
                    "<script>\n" +
                    "function revealDelayed(){ setTimeout(function(){ document.getElementById('delayed').style.display='block'; }, 300); }\n" +
                    "function clickMe(){ document.getElementById('clickResult').textContent='Clicked!'; }\n" +
                    "function onDouble(){ document.getElementById('dblResult').textContent='Double!'; }\n" +
                    "function onChangeSelect(e){ document.getElementById('selValue').textContent=e.target.value; }\n" +
                    "function onKey(e){ document.getElementById('keyResult').textContent=e.key; }\n" +
                    "function onFile(e){ var f=e.target.files && e.target.files[0]; if(f){ e.target.setAttribute('data-file-name', f.name); e.target.setAttribute('data-file-loaded','true'); } }\n" +
                    "function hideSoon(){ setTimeout(function(){ var el=document.getElementById('toHide'); if(el){ el.style.display='none'; } }, 300); }\n" +
                    "</script>\n" +
                    "</head><body onload='revealDelayed();hideSoon()'>\n" +
                    "  <p id='text' data-custom='greeting'>Hello World</p>\n" +
                    "  <input id='name' type='text' value='' onfocus=\"this.setAttribute('data-focused','true')\">\n" +
                    "  <input id='byName' name='username' type='text' value=''>\n" +
                    "  <button id='btn' onclick='clickMe()'>Click</button>\n" +
                    "  <div id='clickResult'></div>\n" +
                    "  <div id='hoverTarget'>Hover me</div>\n" +
                    "  <div id='hoverResult'>Hovered!</div>\n" +
                    "  <div id='delayed'>I appear later</div>\n" +
                    "  <a id='nav' href='" + page2.getFileName().toString() + "'>Go to Page 2</a>\n" +
                    "  <div id='toHide'>I will hide</div>\n" +
                    "  <div id='dbl' ondblclick='onDouble()'>Double Click Me</div>\n" +
                    "  <div id='dblResult'></div>\n" +
                    "  <div id='byClass' class='sample-class'>By Class</div>\n" +
                    "  <select id='sel' onchange='onChangeSelect(event)'>\n" +
                    "    <option value='v1'>Label One</option>\n" +
                    "    <option value='v2'>Label Two</option>\n" +
                    "  </select>\n" +
                    "  <div id='selValue'></div>\n" +
                    "  <input id='chk' type='checkbox' onchange=\"this.setAttribute('data-checked', this.checked ? 'true' : 'false')\">\n" +
                    "  <input id='key' type='text' onkeydown='onKey(event)'>\n" +
                    "  <div id='keyResult'></div>\n" +
                    "  <input id='file' type='file' onchange='onFile(event)'>\n" +
                    "  <div id='byDataTest' data-testid='test-elem'>DataTestId</div>\n" +
                    "  <div id='byRole' role='dialog'>Dialog Role</div>\n" +
                    "  <div id='spacer'></div>\n" +
                    "  <div id='bottom'>Bottom</div>\n" +
                    "</body></html>";
            Files.write(page1, html1.getBytes(StandardCharsets.UTF_8));

            return new Pages(page1, page2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
