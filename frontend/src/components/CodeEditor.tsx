import { useState, useEffect } from 'react';
import Editor, { Monaco } from '@monaco-editor/react';
import { useTheme } from '@/hooks/useTheme';
import { Button } from './ui/button';
import { Select } from './ui/select';
import { Tooltip } from './ui/tooltip';
import { useCollaborationStore } from '@/lib/collaboration-store';

interface CodeEditorProps {
    initialCode?: string;
    language?: string;
    readOnly?: boolean;
    onChange?: (value: string) => void;
}

export function CodeEditor({
    initialCode = '',
    language = 'javascript',
    readOnly = false,
    onChange
}: CodeEditorProps) {
    const [code, setCode] = useState(initialCode);
    const [isExecuting, setIsExecuting] = useState(false);
    const { theme, toggleTheme } = useTheme();
    const { sessionId, addExecutionResult } = useCollaborationStore();

    const handleEditorChange = (value: string = '') => {
        setCode(value);
        onChange?.(value);
        
        if (sessionId) {
            // Broadcast changes to collaboration session
            socket.emit('code-change', {
                sessionId,
                code: value
            });
        }
    };

    const handleExecute = async () => {
        setIsExecuting(true);
        try {
            const response = await fetch('/api/code/execute', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${localStorage.getItem('token')}`
                },
                body: JSON.stringify({
                    code,
                    language
                })
            });

            const result = await response.json();
            addExecutionResult(result);
        } catch (error) {
            addExecutionResult({
                output: '',
                error: 'Failed to execute code: ' + error.message,
                status: 'ERROR',
                executionTime: 0,
                timestamp: Date.now()
            });
        } finally {
            setIsExecuting(false);
        }
    };

    return (
        <div className="flex flex-col h-full">
            <div className="flex justify-between items-center p-2 border-b">
                <div className="flex items-center gap-2">
                    <Select
                        value={language}
                        options={[
                            { value: 'javascript', label: 'JavaScript' },
                            { value: 'python', label: 'Python' },
                            { value: 'java', label: 'Java' },
                            { value: 'typescript', label: 'TypeScript' }
                        ]}
                    />
                    <Button
                        onClick={handleExecute}
                        disabled={isExecuting || readOnly}
                    >
                        {isExecuting ? 'Executing...' : 'Run'}
                    </Button>
                </div>
                <Tooltip content="Toggle theme">
                    <Button
                        variant="ghost"
                        size="icon"
                        onClick={toggleTheme}
                    >
                        {theme === 'vs-dark' ? '🌞' : '🌙'}
                    </Button>
                </Tooltip>
            </div>
            <div className="flex-1">
                <Editor
                    height="100%"
                    language={language}
                    value={code}
                    theme={theme}
                    onChange={handleEditorChange}
                    options={{
                        readOnly,
                        minimap: { enabled: false },
                        fontSize: 14,
                        wordWrap: 'on',
                        automaticLayout: true
                    }}
                />
            </div>
        </div>
    );
}
