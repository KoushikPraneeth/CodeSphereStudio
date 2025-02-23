import { useState } from 'react'
import { Button } from './ui/button'
import { Select } from './ui/select'
import { useEditorStore } from '@/lib/editor-store'

export function CodeConversion() {
    const { code, language } = useEditorStore()
    const [targetLanguage, setTargetLanguage] = useState('')
    const [converting, setConverting] = useState(false)

    const handleConvert = async () => {
        setConverting(true)
        try {
            const response = await fetch('http://localhost:8080/api/ai/convert', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${localStorage.getItem('token')}`
                },
                body: JSON.stringify({
                    sourceCode: code,
                    sourceLanguage: language,
                    targetLanguage
                })
            })

            const reader = response.body?.getReader()
            if (!reader) return

            while (true) {
                const { done, value } = await reader.read()
                if (done) break
                // Handle streaming response...
            }
        } finally {
            setConverting(false)
        }
    }

    return (
        <div className="flex gap-2 items-center">
            <Select
                value={targetLanguage}
                onValueChange={setTargetLanguage}
                options={[
                    { value: 'python', label: 'Python' },
                    { value: 'javascript', label: 'JavaScript' },
                    { value: 'java', label: 'Java' },
                    { value: 'typescript', label: 'TypeScript' }
                ]}
            />
            <Button 
                onClick={handleConvert}
                disabled={converting || !targetLanguage}
            >
                Convert Code
            </Button>
        </div>
    )
}