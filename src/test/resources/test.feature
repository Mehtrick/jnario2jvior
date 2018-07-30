Feature: Calculator
 
Background:
  Calculator calculator
  int result
  Given a calculator
    calculator = new Calculator
  And another calc
  	calculator = new
 
Scenario: Adding two numbers
  When adding two numbers "5:" and "6". 
    result = calculator.add(args.first.toInt, args.second.toInt)
  Then it prints "11"
    result => args.first.toInt
    
Scenario: Dividing two numbers
 When entering two numbers "10" and "5" and pressing enter. 
   result = calculator.divide(args.first.toInt, args.second.toInt)
 Then it prints "2"