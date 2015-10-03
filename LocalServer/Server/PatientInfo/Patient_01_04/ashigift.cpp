#include <iostream>
#include <cstdio>
#include <utility>
#include <set>
#include <algorithm>

using namespace std;
#define ULL int

class point
{
public:
	ULL need,get;
	bool add;

	point()
	{

	}
	point(ULL yi)
	{
		need=yi;
		add=false;
	}

	point(ULL qi,ULL ri)
	{
		need=qi;
		get=ri;
		add=true;
	}

	point(point *p)
	{
		add=p->add;
		get=p->get;
		need=p->need;
	}
};

//typedef pair<ULL,point> pair<ULL,point>;
set< pair<ULL,point> > array;

ULL sum;

int main()
{
	int test;
	scanf("%d",&test);
	while(test--)
	{
		array.clear();
		sum=0;
		ULL dist,dish,xi,yi,clan,pi,qi,ri;
		scanf("%d",&dist);
		scanf("%d",&dish);
		for(ULL i=0;i<dish;i++)
		{
			scanf("%d %d",&xi,&yi);
			sum+=yi;
			pair<ULL,point> p;
			p.first=xi;
			p.second=new point(yi);
			// array.insert(p);
		}

		scanf("%d",&clan);
		// for(ULL i=0;i<clan;i++)
		// {
		// 	scanf("%d %d %d",&pi,&qi,&ri);
		// 	point temp;
		// 	temp.need=qi;
		// 	temp.get=ri;
		// 	temp.add=true;
		// 	pair<ULL,point> p;
		// 	p.first=pi;
		// 	//p.second=temp;
		// 	array.insert(p);
		// }

		ULL group=0,current=0;

		// while((int)array.size())
		// {
		// 	pair<ULL,point> p=*(array.begin());
		// 	if(p.second.add && p.second.need<sum)
		// 	{
		// 		if(p.second.need>current)
		// 		{
		// 			ULL temp=p.second.need - current;
		// 			group+=temp;
		// 			current+=temp;
		// 		}
		// 		current+=p.second.get;
		// 	}
		// 	else if(!p.second.add)
		// 	{
		// 		if(p.second.need>current)
		// 		{
		// 			ULL temp=p.second.need - current;
		// 			group+=temp;
		// 			current+=temp;
		// 		}
		// 		current-=p.second.need;
		// 		sum-=p.second.need;
		// 	}
		// 	array.erase(array.begin());
		// }
		cout<<group<<endl;

	}
	return 0;
}