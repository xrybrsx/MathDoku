import java.util.*;

public class UndoRedo {

    Stack<String> undoStack = new Stack<>();
    Stack<String> redoStack = new Stack<>();

    boolean toggle;

    public Stack getRedoStack() {
        return this.redoStack;
    }

    public Stack getUndoStack() {
        return this.undoStack;
    }
    public void add(String s){
        getUndoStack().push(s);
    }
    public String undo(){
       if (getUndoStack().isEmpty()){
           return null;
       }else {
           if (toggle == false && !getRedoStack().isEmpty()){
               getRedoStack().push(getUndoStack().pop());
           }
            String s = getUndoStack().pop().toString();
            getRedoStack().push(s);
            toggle = true;
           return s;

       }
    }
    public String redo(){
        if (getRedoStack().isEmpty()){
            return null;
        }
        else{ if (toggle == true && !getUndoStack().isEmpty()){
            getUndoStack().push(getRedoStack().pop());
        }
            String s = getRedoStack().pop().toString();
            getUndoStack().push(s);
            toggle = false;
            return s;
            }
         }
    }
