import { useState } from "react";
import { useCollaborationStore } from "@/lib/collaboration-store";
import { useCollaboration } from "@/hooks/useCollaboration";
import { cn } from "@/lib/utils";
import { Button } from "./ui/button";
import { Input } from "./ui/input";
import { Send, Terminal } from "lucide-react";

interface ChatPanelProps {
  className?: string;
}

interface ExecutionResult {
  output: string;
  error: string;
  status: string;
  executionTime: number;
}

export function ChatPanel({ className }: ChatPanelProps) {
  const [message, setMessage] = useState("");
  const { chatMessages, activeUsers, executionResults } =
    useCollaborationStore();
  const { sendChatMessage } = useCollaboration();

  const handleSendMessage = () => {
    if (!message.trim()) return;
    sendChatMessage(message);
    setMessage("");
  };

  const renderExecutionResult = (result: ExecutionResult) => (
    <div className="bg-muted rounded-md p-3">
      <div className="flex items-center gap-2 mb-2">
        <Terminal className="h-4 w-4" />
        <span className="text-sm font-medium">
          Execution Result ({result.executionTime}ms)
        </span>
        <span
          className={cn(
            "text-xs px-2 py-0.5 rounded-full",
            result.status === "SUCCESS"
              ? "bg-green-200 text-green-800"
              : "bg-red-200 text-red-800"
          )}
        >
          {result.status}
        </span>
      </div>
      {result.output && (
        <pre className="text-sm bg-background p-2 rounded overflow-x-auto">
          {result.output}
        </pre>
      )}
      {result.error && (
        <pre className="text-sm bg-red-100 text-red-800 p-2 rounded overflow-x-auto mt-2">
          {result.error}
        </pre>
      )}
    </div>
  );

  return (
    <div className={cn("flex flex-col h-full", className)}>
      <div className="p-4 border-b">
        <h3 className="font-semibold">Collaboration</h3>
        <div className="mt-2">
          <h4 className="text-sm font-medium">Active Users</h4>
          <div className="mt-1 space-y-1">
            {activeUsers.map((user) => (
              <div key={user} className="text-sm text-muted-foreground">
                {user}
              </div>
            ))}
          </div>
        </div>
      </div>
      <div className="flex-1 overflow-y-auto p-4 space-y-4">
        {[...chatMessages, ...executionResults]
          .sort(
            (a, b) =>
              ("timestamp" in a ? a.timestamp : a.executionTime) -
              ("timestamp" in b ? b.timestamp : b.executionTime)
          )
          .map((item, i) => (
            <div key={i}>
              {"message" in item ? (
                <div className="flex flex-col">
                  <span className="text-sm font-medium">{item.username}</span>
                  <span className="text-sm text-muted-foreground">
                    {item.message}
                  </span>
                </div>
              ) : (
                renderExecutionResult(item)
              )}
            </div>
          ))}
      </div>
      <div className="p-4 border-t">
        <div className="flex gap-2">
          <Input
            value={message}
            onChange={(e) => setMessage(e.target.value)}
            onKeyDown={(e) => e.key === "Enter" && handleSendMessage()}
            placeholder="Type a message..."
          />
          <Button size="icon" onClick={handleSendMessage}>
            <Send className="h-4 w-4" />
          </Button>
        </div>
      </div>
    </div>
  );
}
