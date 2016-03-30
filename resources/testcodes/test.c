#include <stdio.h>
int main()
{
    int x, y, z;
    x = 4;
    y = 1;
    z = x * y;

    while (z <= 496)
    {

        printf("%d ", z);
        y++;
        z = x*y;
    }
	getch();
    return 0;
}
