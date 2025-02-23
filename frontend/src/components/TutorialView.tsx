import React from 'react';
import { CodeEditor } from './CodeEditor';
import { Button } from './ui/button';
import { Progress } from './ui/progress';

interface TutorialViewProps {
    tutorial: Tutorial;
    currentStep: number;
    onStepComplete: () => void;
}

export function TutorialView({ tutorial, currentStep, onStepComplete }: TutorialViewProps) {
    return (
        <div className="flex flex-col h-full">
            <div className="flex justify-between items-center p-4">
                <h2 className="text-2xl font-bold">{tutorial.title}</h2>
                <Progress value={(currentStep / tutorial.steps.length) * 100} />
            </div>
            <div className="flex-1">
                <CodeEditor />
            </div>
            <div className="p-4 border-t">
                <Button onClick={onStepComplete}>
                    Complete Step
                </Button>
            </div>
        </div>
    );
}