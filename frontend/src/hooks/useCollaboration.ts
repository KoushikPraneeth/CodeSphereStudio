import { useEffect, useCallback } from "react";
import { websocketService } from "@/lib/websocket";
import { useCollaborationStore } from "@/lib/collaboration-store";
import { useEditorStore } from "@/lib/editor-store";
import { editor } from "monaco-editor";

export function useCollaboration(
  editorRef: React.MutableRefObject<editor.IStandaloneCodeEditor | null>
) {
  const { sessionId, setActiveUsers, updateCursor, addChatMessage } =
    useCollaborationStore();
  const { setCode } = useEditorStore();

  const handleCodeChange = useCallback(
    (newCode: string) => {
      setCode(newCode);
    },
    [setCode]
  );

  const handleCursorMove = useCallback(
    (data: { username: string; position: number }) => {
      updateCursor(data.username, data.position);
    },
    [updateCursor]
  );

  const handleUsersChange = useCallback(
    (users: string[]) => {
      setActiveUsers(users);
    },
    [setActiveUsers]
  );

  const handleChatMessage = useCallback(
    (data: { username: string; message: string }) => {
      addChatMessage(data.username, data.message);
    },
    [addChatMessage]
  );

  useEffect(() => {
    if (!sessionId) return;

    websocketService.handlers.onCodeChange = handleCodeChange;
    websocketService.handlers.onCursorMove = handleCursorMove;
    websocketService.handlers.onUsersChange = handleUsersChange;
    websocketService.handlers.onChatMessage = handleChatMessage;

    websocketService.connect(sessionId);

    return () => {
      websocketService.disconnect();
    };
  }, [
    sessionId,
    handleCodeChange,
    handleCursorMove,
    handleUsersChange,
    handleChatMessage,
  ]);

  return {
    sendCodeChange: (content: string) => {
      if (sessionId) websocketService.sendCodeChange(sessionId, content);
    },
    sendCursorPosition: (position: number) => {
      if (sessionId) websocketService.sendCursorPosition(sessionId, position);
    },
    sendChatMessage: (message: string) => {
      if (sessionId) websocketService.sendChatMessage(sessionId, message);
    },
  };
}
