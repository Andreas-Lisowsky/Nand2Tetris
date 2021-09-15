// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Fill.asm

// Runs an infinite loop that listens to the keyboard input. 
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel. When no key is pressed, the
// program clears the screen, i.e. writes "white" in every pixel.

(endless)
    @SCREEN
    D=A
    @R0
    M=D
    (whilepressed)
        @KBD
        D=M
        @endpressed
        D;JEQ
        @R0
        A=M
        M=-1
        @R0
        M=M+1
        D=M
        @24576
        D=D-A
        @endpressed
        D;JEQ
        @whilepressed
        0;JMP
    (endpressed)
    
    @SCREEN
    D=A
    @R0
    M=D
    (whilenotpressed)
        @KBD
        D=M
        @endnotpressed
        D;JNE
        @R0
        A=M
        M=0
        @R0
        M=M+1
        D=M
        @24576
        D=D-A
        @endnotpressed
        D;JEQ
        @whilenotpressed
        0;JMP
    (endnotpressed)
@endless
0;JMP