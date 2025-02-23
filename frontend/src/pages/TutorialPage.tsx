import { useState, useEffect } from 'react'
import { useParams } from 'react-router-dom'
import { CodeEditor } from '@/components/CodeEditor'
import { Button } from '@/components/ui/button'
import { Progress } from '@/components/ui/progress'

export function TutorialPage() {
    const { id } = useParams()
    const [tutorial, setTutorial] = useState(null)
    const [currentStep, setCurrentStep] = useState(0)

    useEffect(() => {
        fetchTutorial()
    }, [id])

    const fetchTutorial = async () => {
        const response = await fetch(`http://localhost:8080/api/tutorials/${id}`, {
            headers: {
                'Authorization': `Bearer ${localStorage.getItem('token')}`
            }
        })
        const data = await response.json()
        setTutorial(data)
    }

    // Tutorial page implementation...
}