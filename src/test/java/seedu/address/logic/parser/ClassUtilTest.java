package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.switches.HomeCommand;
import seedu.address.logic.commands.switches.OpenCommand;


class ClassUtilTest {

    private ClassUtil classUtil;

    @BeforeEach
    void setUp() {
        classUtil = new ClassUtil();
        classUtil.add(new ClassPair(HomeCommand.class, null));
        classUtil.add(new ClassPair(OpenCommand.class, null));
    }

    @Test
    void add() {
    }

    @Test
    void getAttributeHome() {
        List<String> out = classUtil.getAttribute("COMMAND_WORD");
        boolean found = false;
        for (String s : out) {
            if (s.equals("home")) {
                found = true;
            }
        }
        assertTrue(found);
    }

    @Test
    void getAttributeOpen() {
        List<String> out = classUtil.getAttribute("COMMAND_WORD");
        boolean found = false;
        for (String s : out) {
            if (s.equals("open")) {
                found = true;
            }
        }
        assertTrue(found);
    }

    @Test
    void getCommandInstance() {
    }
}
