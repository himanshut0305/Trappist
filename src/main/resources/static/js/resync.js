class resync {
    var successFlag = 0;
    var completedFlag = 0;
    var errorLog = "";

    constructor(size, func) {
        this.size = size;
        this.func = func;
    }

    increment() {
        successFlag++;
        completedFlag++;

        if(completedFlag === size) {
            if(successFlag === completedFlag)
                func();
            else
                console.log(errorLog);
        }
    }

    decrement(name) {
        completedFlag++;
        errorLog = errorLog + name + "failed. "
    }
}