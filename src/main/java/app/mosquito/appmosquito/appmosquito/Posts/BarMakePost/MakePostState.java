package app.mosquito.appmosquito.appmosquito.Posts.BarMakePost;


public enum MakePostState {

    FAZENDO {
        @Override
        public String toString() {
            return "fazendo";
        }
    }, FINALIZADO {
        @Override
        public String toString() {
            return "finalizado";
        }
    }
}
