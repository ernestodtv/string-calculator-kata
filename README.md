# String Calculator Kata

## The task

### Step 1
Create a simple String calculator with a method signature:

    int Add(string numbers)

The method can take up to two numbers, separated by commas, and will return their sum.

For example “” or “1” or “1,2” as inputs.

For an empty string it will return 0.

### Step 2
Allow the Add method to handle an unknown amount of numbers.

### Step 3
Allow the Add method to handle new lines between numbers (instead of commas):

* The following input is ok: `“1\n2,3”` (will equal 6)
* The following input is NOT ok: `“1,\n”` (not need to prove it - just clarifying)

### Step 4
Support different delimiters:

* To change a delimiter, the beginning of the string will contain a separate line that looks like this: `“//[delimiter]\n[numbers…]”` for example `“//;\n1;2”` should return three where the default delimiter is `‘;’`.
* The first line is optional. All existing scenarios should still be supported.

### Step 5
Calling Add with a negative number will throw an exception `“negatives not allowed”` - and the negative that was passed.

If there are multiple negatives, show all of them in the exception message.

### Step 6
Numbers bigger than 1000 should be ignored, so adding 2 + 1001 = 2

### Step 7
Delimiters can be of any length with the following format: `“//[delimiter]\n”` for example: `“//[***]\n1***2***3”` should return 6.

### Step 8
Allow multiple delimiters like this: `“//[delim1][delim2]\n”` for example `“//[*][%]\n1*2%3”` should return 6.

### Step 9
Make sure you can also handle multiple delimiters with length longer than one char.


## Test cases

- [x] ""  → 0
- [x] "1" → 1
- [x] "2" → 2
- [x] "3" → 3
- [x] "12" → 12
- [x] "1,2" → 3
- [x] "2,5,7" → 14
- [x] "20,30,50" → 100
- [x] "1,2,3,4,5" → 15
- [x] "1\n2,3" → 6
- [x] "//;\n1;2" → 3
- [x] "//.\n2.6.1" → 9
- [x] "//^\n1^2\n4" → 7
- [x] "//[\n1[2" → 3
- [x] "-1,2" → IllegalArgumentException "negatives not allowed: -1"
- [x] "-1,2,3,-4,5" → IllegalArgumentException "negatives not allowed: -1, -4"
- [x] "2,1001" → 2
- [x] "2,1000" → 1002
- [x] "//[\*\*\*]\n1\*\*\*2\*\*\*3" → 6
- [x] "//[..]\n1..2..3\n4" → 10
- [x] "//[^^^]\n2^^^2^^^2^^^2" → 8
- [x] "//[]]]\n1]]2]]3" → 6
- [x] "//[\*\*\*\n1\*\*\*2" → IllegalArgumentException "invalid delimiter definition: [\***"
- [x] "//[]\n12" → IllegalArgumentException "invalid delimiter definition: []"
- [x] "//abc\n1abc2" → IllegalArgumentException "invalid delimiter definition: abc"
- [x] "//[\*][%]\n1\*2%3" → 6
- [x] "//[-][?][}]\n1-2\n3?1}2" → 9
- [x] "//[]][[]\n1]2[2" → 5
- [x] "//[\*\*][%%]\n1\*\*2%%3" → 6
- [x] "//[--][..][,,]\n1--2..3,,4" → 10
- [x] "//[??][¿¿]\n1??2¿¿3??4" → 10