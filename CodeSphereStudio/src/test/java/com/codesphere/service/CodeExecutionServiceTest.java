@SpringBootTest
class CodeExecutionServiceTest {
    @Autowired
    private CodeExecutionService codeExecutionService;

    @Test
    void testPythonCodeExecution() {
        String code = "print('Hello, World!')";
        ExecutionResult result = codeExecutionService.executeCode(code, "python", null);
        
        assertNotNull(result);
        assertEquals("SUCCESS", result.getStatus());
        assertEquals("Hello, World!\n", result.getOutput());
    }

    @Test
    void testJavaScriptCodeExecution() {
        String code = "console.log('Hello, World!');";
        ExecutionResult result = codeExecutionService.executeCode(code, "javascript", null);
        
        assertNotNull(result);
        assertEquals("SUCCESS", result.getStatus());
        assertEquals("Hello, World!\n", result.getOutput());
    }

    @Test
    void testCodeExecutionTimeout() {
        String code = "while True: pass";
        ExecutionResult result = codeExecutionService.executeCode(code, "python", null);
        
        assertNotNull(result);
        assertEquals("ERROR", result.getStatus());
        assertTrue(result.getOutput().contains("timeout"));
    }
}