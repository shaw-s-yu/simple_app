"""
Programmer: Shenghui Yu
Contact: shenghui.yu@live.lagcc.cuny.edu
Date: 3-23-2017
tic tac toe game play
input: the number of move corresbonding to 1 2 3 
                                           4 5 6
                                           7 8 9 of 3x3 board
output: The board and winner

Display the board, everytime set a move
"""

mark=[]

def displayBoard():
    print ('|---|---|---|')
    print ('|', mark[1], '|', mark[2], '|', mark[3], '|')
    print ('|---|---|---|')
    print ('|', mark[4], '|', mark[5], '|', mark[6], '|')
    print ('|---|---|---|')
    print ('|', mark[7], '|', mark[8], '|', mark[9], '|')
    print ('|---|---|---|')

def setMark():
    for turn in range (1, 10):      #this loop excutes 9 times as 9 moves are in
                                    #board
        while True:                 #test value of move input, it can only be
            try:                    #1-9 and cannot be repeated
                move=int(input("Enter a number between 1 to 9: "))
                if move<1 or move>10 or mark[move]=='X' or mark[move]=='O':
                    raise ValueError
                break
            except ValueError:
                print("Invalid value, enter again.")
            
        if turn%2==1:               #set X and O marks as 2 players
            mark[move]='X'
                                    # recored marks are already 
        elif turn%2==0:
            mark[move]='O'

        displayBoard()
        if checkWinner() is True:   #once someone win, loop breaks and this 
            break                   #program ends

def checkWinner():                  #this method runs evertime when a mark is 
                                    #when a mark is set and checks if anyone win

    if mark[1]==mark[2] and mark[2]==mark[3]:
        print("winner here")
        return True
    elif mark[4]==mark[5] and mark[5]==mark[6]:
        print("winner here")
        return True
    elif mark[7]==mark[8] and mark[8]==mark[9]:
        print("winner here")
        return True
    elif mark[1]==mark[4] and mark[7]==mark[4]:
        print("winner here")
        return True
    elif mark[2]==mark[5] and mark[5]==mark[8]:
        print("winner here")
        return True
    elif mark[3]==mark[6] and mark[6]==mark[9]:
        print("winner here")
        return True
    elif mark[1]==mark[5] and mark[5]==mark[9]:
        print("winner here")
        return True
    elif mark[3]==mark[5] and mark[5]==mark[7]:
        print("winner here")
        return True
    else:
        print("no winner")
        return False

for i in range (0,10):
    mark.append(i)
setMark()
    
